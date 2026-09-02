package job.dto;

import java.util.List;

public record CreateJobRequest(String nameVacancy, String nameCompany, List<String> tags, int needExperience) {
}
