package mixss.github_api.services;

import com.fasterxml.jackson.databind.JsonNode;
import mixss.github_api.apiclients.BranchesFromRepoApiClient;
import mixss.github_api.apiclients.ReposFromUserApiClient;
import mixss.github_api.execptions.ApiResponseBadFormatException;
import mixss.github_api.results.BranchResult;
import mixss.github_api.results.ReposAndBranchesResult;
import mixss.github_api.results.RepositoryResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class RepositoryService {

    private final ReposFromUserApiClient reposFromUserApiClient;
    private final BranchesFromRepoApiClient branchesFromRepoApiClient;

    public RepositoryService(ReposFromUserApiClient reposFromUserApiClient, BranchesFromRepoApiClient branchesFromRepoApiClient) {
        this.reposFromUserApiClient = reposFromUserApiClient;
        this.branchesFromRepoApiClient = branchesFromRepoApiClient;
    }

    public ReposAndBranchesResult getReposWithoutForks(String username) {
        List<String> reposList = getAllRepos(username);
        List<RepositoryResult> repositoryResults = new ArrayList<>();
        for(String repo: reposList) {
            List<BranchResult> branchList = getAllBranchesFromRepository(username, repo);
            RepositoryResult repositoryResult = new RepositoryResult(repo, branchList);
            repositoryResults.add(repositoryResult);
        }
        return new ReposAndBranchesResult(username, repositoryResults);
    }

    private List<String> getAllRepos(String username) {
        List<String> reposList = new ArrayList<>();
        JsonNode result = reposFromUserApiClient.getReposFromUser(username);

        for(JsonNode repoData : result) {
            if(!repoData.has("fork")) throw new ApiResponseBadFormatException();
            JsonNode forkNode = repoData.path("fork");

            if(Objects.equals(forkNode.toString(), "false")) {
                if(!repoData.has("name")) throw new ApiResponseBadFormatException();
                JsonNode nameNode = repoData.path("name");
                reposList.add(nameNode.toString().replace("\"", ""));
            }
        }
        return reposList;
    }

    private List<BranchResult> getAllBranchesFromRepository(String username, String repositoryName) {
        List<BranchResult> branchList = new ArrayList<>();
        JsonNode result = branchesFromRepoApiClient.getBranchesFromRepo(username, repositoryName);
        for(JsonNode branch: result) {
            if(!branch.has("name")) throw new ApiResponseBadFormatException();
            JsonNode nameNode = branch.path("name");
            if(!branch.has("commit")) throw new ApiResponseBadFormatException();
            JsonNode commitNode = branch.path("commit");
            if(!commitNode.has("sha")) throw new ApiResponseBadFormatException();
            JsonNode shaNode = commitNode.path("sha");
            branchList.add(new BranchResult(
                            nameNode.toString().replace("\"", ""),
                            shaNode.toString().replace("\"", "")));
        }
        return branchList;
    }
}
