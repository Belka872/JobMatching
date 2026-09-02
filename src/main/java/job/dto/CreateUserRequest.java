package job.dto;

import java.util.List;

public record CreateUserRequest(String name, List<String> skills, int experience) {
}
