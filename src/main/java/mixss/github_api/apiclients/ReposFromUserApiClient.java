package mixss.github_api.apiclients;

import com.fasterxml.jackson.databind.JsonNode;
import mixss.github_api.execptions.ApiErrorReposnseException;
import mixss.github_api.execptions.ApiNotFoundException;
import mixss.github_api.execptions.ApiResponseBadFormatException;
import org.springframework.stereotype.Component;

@Component
public class ReposFromUserApiClient extends ApiClient{
    public JsonNode getReposFromUser(String username) throws ApiErrorReposnseException, ApiNotFoundException, ApiResponseBadFormatException {
        String apiUrl = String.format("https://api.github.com/users/%s/repos", username);
        return makeCall(apiUrl);
    }
}
