package fr.sparkit.crm.util.http;

public class HttpErrorResponse {

    private int code;

    private String message;

    public HttpErrorResponse() {
        super();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setHttpCodeAndMessage(int code, String stream) {
        this.code = code;
        this.message = stream;
    }
}
