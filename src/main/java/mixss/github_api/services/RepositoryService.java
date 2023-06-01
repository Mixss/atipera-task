package mixss.github_api.services;

import com.fasterxml.jackson.databind.JsonNode;
import mixss.github_api.apiclients.ReposFromUserApiClient;
import mixss.github_api.results.RepositoryResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RepositoryService {

    private final ReposFromUserApiClient reposFromUserApiClient;

    public RepositoryService(ReposFromUserApiClient reposFromUserApiClient) {
        this.reposFromUserApiClient = reposFromUserApiClient;
    }

    public RepositoryResult getReposWithoutForks(String username) {
        List<String> reposList = getAllRepos(username);
        return new RepositoryResult(username, reposList);
    }

    private List<String> getAllRepos(String username) {
        List<String> reposList = new ArrayList<>();
        try {
            JsonNode result = reposFromUserApiClient.getReposFromUser(username);
            for(JsonNode repoData : result) {
                System.out.println(repoData.path("full_name"));
                reposList.add(repoData.path("full_name").toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reposList;
    }
}
