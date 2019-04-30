package fr.sparkit.crm.util.http;

import fr.sparkit.crm.util.errors.ErrorsResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HttpCustomException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private int errorCode;
    private ErrorsResponse errorsResponse;

    public HttpCustomException(int errorCode) {
        super();
        this.errorCode = errorCode;
        this.errorsResponse = new ErrorsResponse();
    }

    public HttpCustomException(int errorCode, ErrorsResponse errorsResponse) {
        super();
        this.errorCode = errorCode;
        this.errorsResponse = errorsResponse;
    }


}