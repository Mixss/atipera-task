package mixss.github_api.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mixss.github_api.apiclients.BranchesFromRepoApiClient;
import mixss.github_api.apiclients.ReposFromUserApiClient;
import mixss.github_api.execptions.ApiErrorReposnseException;
import mixss.github_api.execptions.ApiNotFoundException;
import mixss.github_api.execptions.ApiResponseBadFormatException;
import mixss.github_api.results.BranchResult;
import mixss.github_api.results.ReposAndBranchesResult;
import mixss.github_api.results.RepositoryResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class RepositoryServiceTest {

    RepositoryService service;
    String username = "Mixss";
    String repositoryName = "statystyk-codzienny";
    String repoApiResult = "[\n" +
            "  {\n" +
            "    \"name\": \"holidays\",\n" +
            "    \"commit\": {\n" +
            "      \"sha\": \"debba4b4a6e2e3c25a92d4f492b118023b22819a\",\n" +
            "      \"url\": \"https://api.github.com/repos/Mixss/statystyk-codzienny/commits/debba4b4a6e2e3c25a92d4f492b118023b22819a\"\n" +
            "    },\n" +
            "    \"protected\": false\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\": \"master\",\n" +
            "    \"commit\": {\n" +
            "      \"sha\": \"ab8010e673219b8d4ac4bbdbf17d19c39251b3b5\",\n" +
            "      \"url\": \"https://api.github.com/repos/Mixss/statystyk-codzienny/commits/ab8010e673219b8d4ac4bbdbf17d19c39251b3b5\"\n" +
            "    },\n" +
            "    \"protected\": false\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\": \"new\",\n" +
            "    \"commit\": {\n" +
            "      \"sha\": \"b75f21e52f43d507480f9ca9b56afdd84fd78289\",\n" +
            "      \"url\": \"https://api.github.com/repos/Mixss/statystyk-codzienny/commits/b75f21e52f43d507480f9ca9b56afdd84fd78289\"\n" +
            "    },\n" +
            "    \"protected\": false\n" +
            "  }\n" +
            "]";
    String reposApiResult = "[\n" +
            "  {\n" +
            "    \"id\": 475567908,\n" +
            "    \"node_id\": \"R_kgDOHFiXJA\",\n" +
            "    \"name\": \"statystyk-codzienny\",\n" +
            "    \"full_name\": \"Mixss/statystyk-codzienny\",\n" +
            "    \"private\": false,\n" +
            "    \"owner\": {\n" +
            "      \"login\": \"Mixss\",\n" +
            "      \"id\": 19227717,\n" +
            "      \"node_id\": \"MDQ6VXNlcjE5MjI3NzE3\",\n" +
            "      \"avatar_url\": \"https://avatars.githubusercontent.com/u/19227717?v=4\",\n" +
            "      \"gravatar_id\": \"\",\n" +
            "      \"url\": \"https://api.github.com/users/Mixss\",\n" +
            "      \"html_url\": \"https://github.com/Mixss\",\n" +
            "      \"followers_url\": \"https://api.github.com/users/Mixss/followers\",\n" +
            "      \"following_url\": \"https://api.github.com/users/Mixss/following{/other_user}\",\n" +
            "      \"gists_url\": \"https://api.github.com/users/Mixss/gists{/gist_id}\",\n" +
            "      \"starred_url\": \"https://api.github.com/users/Mixss/starred{/owner}{/repo}\",\n" +
            "      \"subscriptions_url\": \"https://api.github.com/users/Mixss/subscriptions\",\n" +
            "      \"organizations_url\": \"https://api.github.com/users/Mixss/orgs\",\n" +
            "      \"repos_url\": \"https://api.github.com/users/Mixss/repos\",\n" +
            "      \"events_url\": \"https://api.github.com/users/Mixss/events{/privacy}\",\n" +
            "      \"received_events_url\": \"https://api.github.com/users/Mixss/received_events\",\n" +
            "      \"type\": \"User\",\n" +
            "      \"site_admin\": false\n" +
            "    },\n" +
            "    \"html_url\": \"https://github.com/Mixss/statystyk-codzienny\",\n" +
            "    \"description\": \"Discord bot with everyday statistics\",\n" +
            "    \"fork\": false,\n" +
            "    \"url\": \"https://api.github.com/repos/Mixss/statystyk-codzienny\",\n" +
            "    \"forks_url\": \"https://api.github.com/repos/Mixss/statystyk-codzienny/forks\",\n" +
            "    \"keys_url\": \"https://api.github.com/repos/Mixss/statystyk-codzienny/keys{/key_id}\",\n" +
            "    \"collaborators_url\": \"https://api.github.com/repos/Mixss/statystyk-codzienny/collaborators{/collaborator}\",\n" +
            "    \"teams_url\": \"https://api.github.com/repos/Mixss/statystyk-codzienny/teams\",\n" +
            "    \"hooks_url\": \"https://api.github.com/repos/Mixss/statystyk-codzienny/hooks\",\n" +
            "    \"issue_events_url\": \"https://api.github.com/repos/Mixss/statystyk-codzienny/issues/events{/number}\",\n" +
            "    \"events_url\": \"https://api.github.com/repos/Mixss/statystyk-codzienny/events\",\n" +
            "    \"assignees_url\": \"https://api.github.com/repos/Mixss/statystyk-codzienny/assignees{/user}\",\n" +
            "    \"branches_url\": \"https://api.github.com/repos/Mixss/statystyk-codzienny/branches{/branch}\",\n" +
            "    \"tags_url\": \"https://api.github.com/repos/Mixss/statystyk-codzienny/tags\",\n" +
            "    \"blobs_url\": \"https://api.github.com/repos/Mixss/statystyk-codzienny/git/blobs{/sha}\",\n" +
            "    \"git_tags_url\": \"https://api.github.com/repos/Mixss/statystyk-codzienny/git/tags{/sha}\",\n" +
            "    \"git_refs_url\": \"https://api.github.com/repos/Mixss/statystyk-codzienny/git/refs{/sha}\",\n" +
            "    \"trees_url\": \"https://api.github.com/repos/Mixss/statystyk-codzienny/git/trees{/sha}\",\n" +
            "    \"statuses_url\": \"https://api.github.com/repos/Mixss/statystyk-codzienny/statuses/{sha}\",\n" +
            "    \"languages_url\": \"https://api.github.com/repos/Mixss/statystyk-codzienny/languages\",\n" +
            "    \"stargazers_url\": \"https://api.github.com/repos/Mixss/statystyk-codzienny/stargazers\",\n" +
            "    \"contributors_url\": \"https://api.github.com/repos/Mixss/statystyk-codzienny/contributors\",\n" +
            "    \"subscribers_url\": \"https://api.github.com/repos/Mixss/statystyk-codzienny/subscribers\",\n" +
            "    \"subscription_url\": \"https://api.github.com/repos/Mixss/statystyk-codzienny/subscription\",\n" +
            "    \"commits_url\": \"https://api.github.com/repos/Mixss/statystyk-codzienny/commits{/sha}\",\n" +
            "    \"git_commits_url\": \"https://api.github.com/repos/Mixss/statystyk-codzienny/git/commits{/sha}\",\n" +
            "    \"comments_url\": \"https://api.github.com/repos/Mixss/statystyk-codzienny/comments{/number}\",\n" +
            "    \"issue_comment_url\": \"https://api.github.com/repos/Mixss/statystyk-codzienny/issues/comments{/number}\",\n" +
            "    \"contents_url\": \"https://api.github.com/repos/Mixss/statystyk-codzienny/contents/{+path}\",\n" +
            "    \"compare_url\": \"https://api.github.com/repos/Mixss/statystyk-codzienny/compare/{base}...{head}\",\n" +
            "    \"merges_url\": \"https://api.github.com/repos/Mixss/statystyk-codzienny/merges\",\n" +
            "    \"archive_url\": \"https://api.github.com/repos/Mixss/statystyk-codzienny/{archive_format}{/ref}\",\n" +
            "    \"downloads_url\": \"https://api.github.com/repos/Mixss/statystyk-codzienny/downloads\",\n" +
            "    \"issues_url\": \"https://api.github.com/repos/Mixss/statystyk-codzienny/issues{/number}\",\n" +
            "    \"pulls_url\": \"https://api.github.com/repos/Mixss/statystyk-codzienny/pulls{/number}\",\n" +
            "    \"milestones_url\": \"https://api.github.com/repos/Mixss/statystyk-codzienny/milestones{/number}\",\n" +
            "    \"notifications_url\": \"https://api.github.com/repos/Mixss/statystyk-codzienny/notifications{?since,all,participating}\",\n" +
            "    \"labels_url\": \"https://api.github.com/repos/Mixss/statystyk-codzienny/labels{/name}\",\n" +
            "    \"releases_url\": \"https://api.github.com/repos/Mixss/statystyk-codzienny/releases{/id}\",\n" +
            "    \"deployments_url\": \"https://api.github.com/repos/Mixss/statystyk-codzienny/deployments\",\n" +
            "    \"created_at\": \"2022-03-29T18:21:31Z\",\n" +
            "    \"updated_at\": \"2023-02-11T12:16:12Z\",\n" +
            "    \"pushed_at\": \"2023-03-22T08:17:10Z\",\n" +
            "    \"git_url\": \"git://github.com/Mixss/statystyk-codzienny.git\",\n" +
            "    \"ssh_url\": \"git@github.com:Mixss/statystyk-codzienny.git\",\n" +
            "    \"clone_url\": \"https://github.com/Mixss/statystyk-codzienny.git\",\n" +
            "    \"svn_url\": \"https://github.com/Mixss/statystyk-codzienny\",\n" +
            "    \"homepage\": null,\n" +
            "    \"size\": 2958,\n" +
            "    \"stargazers_count\": 1,\n" +
            "    \"watchers_count\": 1,\n" +
            "    \"language\": \"Python\",\n" +
            "    \"has_issues\": true,\n" +
            "    \"has_projects\": true,\n" +
            "    \"has_downloads\": true,\n" +
            "    \"has_wiki\": false,\n" +
            "    \"has_pages\": false,\n" +
            "    \"has_discussions\": true,\n" +
            "    \"forks_count\": 1,\n" +
            "    \"mirror_url\": null,\n" +
            "    \"archived\": false,\n" +
            "    \"disabled\": false,\n" +
            "    \"open_issues_count\": 3,\n" +
            "    \"license\": null,\n" +
            "    \"allow_forking\": true,\n" +
            "    \"is_template\": false,\n" +
            "    \"web_commit_signoff_required\": false,\n" +
            "    \"topics\": [\n" +
            "\n" +
            "    ],\n" +
            "    \"visibility\": \"public\",\n" +
            "    \"forks\": 1,\n" +
            "    \"open_issues\": 3,\n" +
            "    \"watchers\": 1,\n" +
            "    \"default_branch\": \"master\"\n" +
            "  }\n" +
            "]";

    @Mock
    ReposFromUserApiClient reposFromUserApiClient;
    @Mock
    BranchesFromRepoApiClient branchesFromRepoApiClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new RepositoryService(reposFromUserApiClient, branchesFromRepoApiClient);
    }

    @Test
    void getReposWithoutForks_ReturnsCorrectValue_WhenCorrectApiResult() throws ApiErrorReposnseException, ApiNotFoundException, ApiResponseBadFormatException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        when(reposFromUserApiClient.getReposFromUser(username)).thenReturn(mapper.readTree(reposApiResult));
        when(branchesFromRepoApiClient.getBranchesFromRepo(username, repositoryName)).thenReturn(mapper.readTree(repoApiResult));

        // expected result
        List<BranchResult> expectedBranches = List.of(new BranchResult("holidays", "debba4b4a6e2e3c25a92d4f492b118023b22819a"), new BranchResult("master", "ab8010e673219b8d4ac4bbdbf17d19c39251b3b5"), new BranchResult("new", "b75f21e52f43d507480f9ca9b56afdd84fd78289"));
        List<RepositoryResult> expectedRepositories = List.of(new RepositoryResult(repositoryName, expectedBranches));
        ReposAndBranchesResult expectedResult = new ReposAndBranchesResult(username, expectedRepositories);
        ReposAndBranchesResult result = service.getReposWithoutForks(username);
        assertEquals(expectedResult, result);
    }

}