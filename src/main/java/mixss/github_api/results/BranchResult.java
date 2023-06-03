package mixss.github_api.results;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class BranchResult {
    private String name;
    private String lastCommitSha;
}
