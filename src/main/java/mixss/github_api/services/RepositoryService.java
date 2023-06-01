package mixss.github_api.services;

import mixss.github_api.results.RepositoryResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepositoryService {

    public RepositoryResult getReposWithoutForks(String username) {
        return new RepositoryResult(username, List.of("test1", "test2"));
    }
}
