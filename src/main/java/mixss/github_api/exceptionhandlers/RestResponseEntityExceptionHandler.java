package mixss.github_api.exceptionhandlers;

import mixss.github_api.execptions.ApiErrorReposnseException;
import mixss.github_api.execptions.ApiException;
import mixss.github_api.execptions.ApiNotFoundException;
import mixss.github_api.results.ErrorResult;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {
            ApiNotFoundException.class, ApiErrorReposnseException.class, ApiErrorReposnseException.class})
    protected ResponseEntity<Object> handleException(ApiException e, WebRequest request) {
        ErrorResult errorResult = new ErrorResult(e.getHttpStatus().value(), e.getMessage());
        return handleExceptionInternal(e, errorResult, new HttpHeaders(), e.getHttpStatus(), request);
    }
}
