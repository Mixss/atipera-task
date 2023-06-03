package mixss.github_api.results;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class RepositoryResult {
    private String name;
    private List<BranchResult> branches;
}
