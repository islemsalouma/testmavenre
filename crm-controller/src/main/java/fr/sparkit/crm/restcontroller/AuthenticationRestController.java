package fr.sparkit.crm.restcontroller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.sparkit.crm.services.impl.JwtAuthenticationResponse;
import fr.sparkit.crm.services.impl.JwtTokenUtil;
import fr.sparkit.crm.services.impl.JwtUser;

@RestController
public class AuthenticationRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationRestController.class);

    @Value("${jwt.header}")
    private String tokenHeader;

    private UserDetailsService userDetailsService;

    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    public AuthenticationRestController(JwtTokenUtil jwtTokenUtil, UserDetailsService userDetailsService) {
        super();
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    @RequestMapping(value = "/authentication-manager/switch-user", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('impersonate_USER')")
    public ResponseEntity<JwtAuthenticationResponse> createSwitchUserToken(HttpServletRequest request,
            @RequestParam String username, Device device) {
        final String originalUser = request.getUserPrincipal().getName();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        LOGGER.info("Switching current user to {}", username);

        List<GrantedAuthority> authorities = (List<GrantedAuthority>) userDetails.getAuthorities();
        authorities.add(new SimpleGrantedAuthority("previous_ADMIN"));
        List<GrantedAuthority> impersonationAuthorities = new ArrayList<>(authorities);

        UserDetails newUserDetails = org.springframework.security.core.userdetails.User.withUsername(username)
                .authorities(impersonationAuthorities).password("justinventedhere").build();

        Authentication userPasswordAuthentication = new UsernamePasswordAuthenticationToken(newUserDetails, null,
                impersonationAuthorities);

        SecurityContextHolder.getContext().setAuthentication(userPasswordAuthentication);

        final String token = jwtTokenUtil.generateToken(userDetails, device);
        String message = "Switch " + originalUser + " to " + username;
        return ResponseEntity.ok(new JwtAuthenticationResponse(token, (JwtUser) userDetails));
    }

    @RequestMapping(value = "/authentication-manager/authentication", method = RequestMethod.POST)
    public ResponseEntity<JwtAuthenticationResponse> createAuthenticationToken(HttpServletRequest request,
            @RequestBody JwtAuthenticationRequest authenticationRequest, Device device) {
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @RequestMapping(value = "/authentication-manager/refresh", method = RequestMethod.GET)
    public ResponseEntity<JwtAuthenticationResponse> refreshAndGetAuthenticationToken(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);

        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
            String refreshedToken = jwtTokenUtil.refreshToken(token);
            return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken, user));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @RequestMapping(value = "/authentication-manager/logout", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public void logoutMe(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String principal = jwtTokenUtil.getUsernameFromToken(token);
        LOGGER.info(principal, " is logging out ..");
        SecurityContextHolder.clearContext();
        LOGGER.info(principal, " has logged out successfully");
    }

}
