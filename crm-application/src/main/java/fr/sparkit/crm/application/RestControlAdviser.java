// package fr.sparkit.crm.application;
//
// import fr.sparkit.crm.util.http.HttpCustomException;
// import fr.sparkit.crm.util.http.HttpErrorResponse;
//
// import org.springframework.web.bind.annotation.ExceptionHandler;
// import org.springframework.web.bind.annotation.RestControllerAdvice;
//
// import javax.servlet.http.HttpServletResponse;
//
/// **
// * @author amine
// * @Configuration class.
// * Represents a global intercepter/hook on for every
// * Exception thrown by any class annotated with @RestController.
// */
// @RestControllerAdvice()
// public class RestControlAdviser {
//
// private static final int CUSTOM_STATUS_CODE = 223;
//
// @ExceptionHandler(HttpCustomException.class)
// public HttpErrorResponse handleCustomerException(HttpCustomException
// exception,
// HttpServletResponse response) {
// HttpErrorResponse httpErrorResponse = new
// HttpErrorResponse(exception.getErrorCode(),
// exception.getErrorsResponse());
// response.setStatus(CUSTOM_STATUS_CODE);
// return httpErrorResponse;
// }
// }
