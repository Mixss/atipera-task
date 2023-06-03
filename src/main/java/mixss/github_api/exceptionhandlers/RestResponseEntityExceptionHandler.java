package mixss.github_api.exceptionhandlers;

import mixss.github_api.execptions.*;
import mixss.github_api.results.ErrorResult;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.lang.invoke.WrongMethodTypeException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {
            ApiNotFoundException.class, ApiErrorReposnseException.class, ApiResponseBadFormatException.class,
            ApiWrongAcceptHeaderException.class})
    protected ResponseEntity<Object> handleException(RuntimeException e, WebRequest request) {
        if(!(e instanceof ApiException ae)) {
            return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), HttpStatus.BAD_GATEWAY, request);
        }
        ErrorResult errorResult = new ErrorResult(ae.getHttpStatus().value(), ae.getMessage());
        return handleExceptionInternal(ae, errorResult, new HttpHeaders(), ae.getHttpStatus(), request);
    }

    @Override
    @ResponseBody
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String result = String.format("{\n\t\"status\": %d,\n\t\"Message\": %s\n}", HttpStatus.NOT_ACCEPTABLE.value(), "Accept header not acceptable");
        return new ResponseEntity<>(result, HttpStatus.NOT_ACCEPTABLE);
    }
}
