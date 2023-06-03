package mixss.github_api.results;

import lombok.*;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ReposAndBranchesResult {
    private String owner;
    private List<RepositoryResult> repositories;
}
