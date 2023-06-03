package mixss.github_api.execptions;

import org.springframework.http.HttpStatus;

public class ApiNotFoundException extends ApiException{

    public ApiNotFoundException() {
        super("Data not found", HttpStatus.NOT_FOUND);
    }
}
