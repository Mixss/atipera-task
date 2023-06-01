package mixss.github_api.apiclients;

import com.fasterxml.jackson.databind.JsonNode;
import mixss.github_api.execptions.ApiErrorReposnseException;
import mixss.github_api.execptions.ApiNotFoundException;
import mixss.github_api.execptions.ApiResponseBadFormatException;
import org.springframework.stereotype.Component;

@Component
public class BranchesFromRepoApiClient extends ApiClient{
    public JsonNode getBranchesFromRepo(String username, String repoName) throws ApiErrorReposnseException, ApiNotFoundException, ApiResponseBadFormatException {
        String apiUrl = String.format("https://api.github.com/repos/%s/%s/branches", username, repoName);
        return makeCall(apiUrl);
    }
}
