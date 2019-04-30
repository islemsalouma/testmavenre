// package fr.sparkit.crm.application;
//
// import java.io.IOException;
// import java.io.Serializable;
// import java.nio.charset.StandardCharsets;
//
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
//
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.MediaType;
// import org.springframework.security.core.AuthenticationException;
// import org.springframework.security.web.AuthenticationEntryPoint;
// import org.springframework.stereotype.Component;
//
// import fr.sparkit.crm.utils.ApiError;
//
// @Component
// public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint,
// Serializable {
//
// private static final long serialVersionUID = 1L;
//
// private static final Logger LOGGER =
// LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);
//
// @Autowired
// private ObjectMapperFactory jackson2JsonObjectMapper;
//
// JwtAuthenticationEntryPoint(ObjectMapperFactory jackson2JsonObjectMapper) {
// this.jackson2JsonObjectMapper = jackson2JsonObjectMapper;
// }
//
// @Override
// public void commence(HttpServletRequest request, HttpServletResponse
// response, AuthenticationException exception)
// throws IOException {
//
// ApiError error = new ApiError(HttpStatus.FORBIDDEN,
// exception.getLocalizedMessage(), exception.getMessage());
// try {
// String json = jackson2JsonObjectMapper.toJson(error);
// response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
// response.setContentType(MediaType.APPLICATION_JSON_VALUE);
// response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
// response.getWriter().write(json);
// } catch (Exception e) {
// LOGGER.error("An error occurred in the commence method of
// JwtAuthenticationEntryPoint component", e);
// }
// }
// }
