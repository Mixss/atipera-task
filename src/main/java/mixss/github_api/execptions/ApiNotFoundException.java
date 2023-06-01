package mixss.github_api.execptions;

public class ApiNotFoundException extends Exception{

    public ApiNotFoundException() {
        super("Data not found");
    }
}
