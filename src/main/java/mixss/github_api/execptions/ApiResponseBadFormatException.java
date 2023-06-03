package mixss.github_api.execptions;

import org.springframework.http.HttpStatus;

public class ApiResponseBadFormatException extends ApiException{

    // this exception is thrown when outside API returns data in changed/unsupported format
    public ApiResponseBadFormatException() {
        super("Unable to provide data", HttpStatus.NOT_IMPLEMENTED);
    }
}
