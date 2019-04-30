package fr.sparkit.crm.services.impl;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;

@Component
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = -3301605591108950415L;

    static final String CLAIM_KEY_USERNAME = "sub";

    static final String CLAIM_KEY_AUDIENCE = "audience";
    static final String CLAIM_KEY_CREATED = "created";

    private static final String AUDIENCE_UNKNOWN = "unknown";
    private static final String AUDIENCE_WEB = "web";
    private static final String AUDIENCE_MOBILE = "mobile";
    private static final String AUDIENCE_TABLET = "tablet";
    private static final String MICROSOFT_PUBLIC_KEYS_URL = "https://login.microsoftonline.com/common/discovery/keys";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String getUsernameFromToken(String token) {
        String username = null;
        final Claims claims = getClaimsFromToken(token);
        if (claims != null) {
            username = claims.getSubject();
        }
        return username;
    }

    public Date getCreatedDateFromToken(String token) {
        Date created = null;
        final Claims claims = getClaimsFromToken(token);
        if (claims != null) {
            created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
        }
        return created;
    }

    public Date getExpirationDateFromToken(String token) {
        Date expirationDate = null;
        final Claims claims = getClaimsFromToken(token);
        if (claims != null) {
            expirationDate = claims.getExpiration();
        }
        return expirationDate;
    }

    public String getAudienceFromToken(String token) {
        String audience = null;
        final Claims claims = getClaimsFromToken(token);
        if (claims != null) {
            audience = (String) claims.get(CLAIM_KEY_AUDIENCE);
        }
        return audience;
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    private Boolean isTokenExpired(String token) {
        final Date expirationDate = getExpirationDateFromToken(token);
        return expirationDate.before(new Date());
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    private String generateAudience(Device device) {
        String audience = AUDIENCE_UNKNOWN;
        if (device.isNormal()) {
            audience = AUDIENCE_WEB;
        } else if (device.isTablet()) {
            audience = AUDIENCE_TABLET;
        } else if (device.isMobile()) {
            audience = AUDIENCE_MOBILE;
        }
        return audience;
    }

    private Boolean ignoreTokenExpiration(String token) {
        String audience = getAudienceFromToken(token);
        return (AUDIENCE_TABLET.equals(audience) || AUDIENCE_MOBILE.equals(audience));
    }

    public String generateToken(UserDetails userDetails, Device device) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_AUDIENCE, generateAudience(device));
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    String generateToken(Map<String, Object> claims) {
        return Jwts.builder().setClaims(claims).setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.RS256, secret).compact();
    }

    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = getCreatedDateFromToken(token);
        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
                && (!isTokenExpired(token) || ignoreTokenExpiration(token));
    }

    public String refreshToken(String token) {
        String refreshedToken = null;
        final Claims claims = getClaimsFromToken(token);
        if (claims != null) {
            claims.put(CLAIM_KEY_CREATED, new Date());
            refreshedToken = generateToken(claims);
        }
        return refreshedToken;
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        JwtUser user = (JwtUser) userDetails;
        final String username = getUsernameFromToken(token);
        final Date created = getCreatedDateFromToken(token);
        return (username.equals(user.getUsername()) && !isTokenExpired(token)
                && !isCreatedBeforeLastPasswordReset(created, user.getLastPasswordResetDate()));
    }

    private Claims getClaimsFromAzureActiveDirectoryToken(String token) {
        Claims claims;
        JWKSet jwkSet;

        try {
            String[] split = token.split("\\.");
            byte[] header = TextCodec.BASE64URL.decode(split[0]);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode headerJson = objectMapper.readTree(new String(header, StandardCharsets.UTF_8));
            jwkSet = JWKSet.load(new URL(MICROSOFT_PUBLIC_KEYS_URL));
            JWK jwk = jwkSet.getKeyByKeyId(headerJson.get("kid").asText());
            jwk.toJSONObject();
            String e = jwk.toJSONObject().getAsString("e");
            String n = jwk.toJSONObject().getAsString("n");
            byte[] exponent = TextCodec.BASE64.decode(e);
            byte[] modulus = TextCodec.BASE64URL.decode(n);
            RSAPublicKeySpec publicSpec = new RSAPublicKeySpec(new BigInteger(1, modulus), new BigInteger(1, exponent));
            KeyFactory factory = KeyFactory.getInstance("RSA");
            PublicKey pupKey = factory.generatePublic(publicSpec);
            claims = Jwts.parser().setSigningKey(pupKey).parseClaimsJws(token).getBody();
            return claims;
        } catch (RuntimeException | NoSuchAlgorithmException | InvalidKeySpecException | IOException
                | ParseException e) {
            return null;
        }
    }

    public Date getExpirationDateFromAzureActiveDirectoryToken(String token) {
        Date expirationDate = null;
        Claims claims = getClaimsFromAzureActiveDirectoryToken(token);
        if (claims != null) {
            expirationDate = claims.getExpiration();
        }
        return expirationDate;
    }

    private Boolean isFromAzureActiveDirectoryToken(String token) {
        Date expirationDate = getExpirationDateFromAzureActiveDirectoryToken(token);

        if (expirationDate != null) {
            return expirationDate.before(new Date());
        }
        return true;
    }

    public Boolean validateTokenFromAzureActiveDirectory(String token) {
        return !isFromAzureActiveDirectoryToken(token);
    }

}
