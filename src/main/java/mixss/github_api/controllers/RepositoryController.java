package mixss.github_api.controllers;

import mixss.github_api.execptions.ApiNotFoundException;
import mixss.github_api.execptions.ApiWrongAcceptHeaderException;
import mixss.github_api.results.ReposAndBranchesResult;
import mixss.github_api.results.RepositoryResult;
import mixss.github_api.services.RepositoryService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
//@RestControllerAdvice
public class RepositoryController {

    private final RepositoryService repositoryService;

    public RepositoryController(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @GetMapping(value = "/repos/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReposAndBranchesResult> getReposWithoutForks(@PathVariable String username) {
        ReposAndBranchesResult result = repositoryService.getReposWithoutForks(username);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
