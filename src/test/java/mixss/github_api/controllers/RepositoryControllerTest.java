package mixss.github_api.controllers;

import mixss.github_api.results.BranchResult;
import mixss.github_api.results.ReposAndBranchesResult;
import mixss.github_api.results.RepositoryResult;
import mixss.github_api.services.RepositoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

class RepositoryControllerTest {

    RepositoryController controller;
    String username = "Mixss";
    String repositoryName = "statystyk-codzienny";
    ReposAndBranchesResult serviceResult;

    @Mock
    RepositoryService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        List<BranchResult> expectedBranches = List.of(new BranchResult("holidays", "debba4b4a6e2e3c25a92d4f492b118023b22819a"), new BranchResult("master", "ab8010e673219b8d4ac4bbdbf17d19c39251b3b5"), new BranchResult("new", "b75f21e52f43d507480f9ca9b56afdd84fd78289"));
        List<RepositoryResult> expectedRepositories = List.of(new RepositoryResult(repositoryName, expectedBranches));
        serviceResult = new ReposAndBranchesResult(username, expectedRepositories);

        controller = new RepositoryController(service);
    }

    @Test
    void getReposWithoutForks_returnsCorrectResponse() {
        when(service.getReposWithoutForks(username)).thenReturn(serviceResult);
        ResponseEntity<ReposAndBranchesResult> expectedResult = new ResponseEntity<>(serviceResult, HttpStatus.OK);

        assertEquals(expectedResult, controller.getReposWithoutForks(username));
    }
}