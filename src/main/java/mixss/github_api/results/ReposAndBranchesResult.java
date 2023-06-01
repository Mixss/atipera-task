package mixss.github_api.results;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReposAndBranchesResult {
    private String owner;
    private List<RepositoryResult> repositories;
}
