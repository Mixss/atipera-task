package mixss.github_api.execptions;

import org.springframework.http.HttpStatus;

public class ApiErrorReposnseException extends ApiException{
    public ApiErrorReposnseException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
