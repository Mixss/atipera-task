package mixss.github_api.controllers;

import mixss.github_api.execptions.ApiNotFoundException;
import mixss.github_api.results.ReposAndBranchesResult;
import mixss.github_api.results.RepositoryResult;
import mixss.github_api.services.RepositoryService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RepositoryController {

    private final RepositoryService repositoryService;

    public RepositoryController(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @GetMapping("/repos/{username}")
    public ResponseEntity<ReposAndBranchesResult> getReposWithoutForks(@RequestHeader(HttpHeaders.ACCEPT) String acceptHeader, @PathVariable String username) {
        ReposAndBranchesResult result = repositoryService.getReposWithoutForks(username);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
