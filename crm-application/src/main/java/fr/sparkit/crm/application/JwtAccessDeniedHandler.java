// package fr.sparkit.crm.application;
//
// import java.io.IOException;
// import java.nio.charset.StandardCharsets;
//
// import javax.servlet.ServletException;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
//
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.MediaType;
// import org.springframework.integration.support.json.Jackson2JsonObjectMapper;
// import org.springframework.security.access.AccessDeniedException;
// import org.springframework.security.web.access.AccessDeniedHandler;
// import org.springframework.stereotype.Component;
//
// import fr.sparkit.crm.utils.ApiError;
//
// @Component
// public class JwtAccessDeniedHandler implements AccessDeniedHandler {
//
// private static final Logger LOGGER =
// LoggerFactory.getLogger(JwtAccessDeniedHandler.class);
//
// @Autowired
// public Jackson2JsonObjectMapper jackson2JsonObjectMapper;
//
// @Override
// public void handle(HttpServletRequest request, HttpServletResponse response,
// AccessDeniedException exception)
// throws IOException, ServletException {
//
// ApiError error = new ApiError(HttpStatus.FORBIDDEN,
// exception.getLocalizedMessage(), exception.getMessage());
// try {
// String json = jackson2JsonObjectMapper.toJson(error);
// response.setStatus(HttpServletResponse.SC_FORBIDDEN);
// response.setContentType(MediaType.APPLICATION_JSON_VALUE);
// response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
// response.getWriter().write(json);
// } catch (Exception e) {
// LOGGER.error("An error occurred in the handle method of
// JwtAccessDeniedHandler component", e);
// }
// }
// }
