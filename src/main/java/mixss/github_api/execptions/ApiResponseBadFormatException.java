package mixss.github_api.execptions;

public class ApiResponseBadFormatException extends Exception{

    // this exception is thrown when outside API returns data in changed/unsupported format
    public ApiResponseBadFormatException() {
        super("Unable to provide data");
    }
}
