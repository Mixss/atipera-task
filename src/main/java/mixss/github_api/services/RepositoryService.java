package mixss.github_api.services;

import com.fasterxml.jackson.databind.JsonNode;
import mixss.github_api.apiclients.BranchesFromRepoApiClient;
import mixss.github_api.apiclients.ReposFromUserApiClient;
import mixss.github_api.results.BranchResult;
import mixss.github_api.results.ReposAndBranchesResult;
import mixss.github_api.results.RepositoryResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        try {
            JsonNode result = reposFromUserApiClient.getReposFromUser(username);
            for(JsonNode repoData : result) {
                reposList.add(repoData.path("name").toString().replace("\"", ""));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reposList;
    }

    private List<BranchResult> getAllBranchesFromRepository(String username, String repositoryName) {
        List<BranchResult> branchList = new ArrayList<>();
        try {
            JsonNode result = branchesFromRepoApiClient.getBranchesFromRepo(username, repositoryName);
            for(JsonNode branch: result) {
                branchList.add(new BranchResult(
                                branch.path("name").toString().replace("\"", ""),
                                branch.path("commit").path("sha").toString().replace("\"", "")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return branchList;
    }
}
