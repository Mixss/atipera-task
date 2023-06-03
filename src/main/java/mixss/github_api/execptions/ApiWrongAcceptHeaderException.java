package mixss.github_api.execptions;

import org.springframework.http.HttpStatus;

public class ApiWrongAcceptHeaderException extends ApiException{
    // this exception is thrown when api gets request with header "Accept: application/xml"
    public ApiWrongAcceptHeaderException() {
        super("application/xml header is not acceptable", HttpStatus.NOT_ACCEPTABLE);
    }
}
