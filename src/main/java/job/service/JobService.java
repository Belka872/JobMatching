package job.service;

import job.domain.Vacancy;
import job.repository.JobRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class JobService {
    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public void createVacancy(String name, String company, List<String> tags, int experience) {
        List<String> normalizedTags = tags.stream()
                .distinct()
                .sorted()
                .toList();
        jobRepository.addVacancy(new Vacancy(name, company, normalizedTags, experience));
    }

    public List<Vacancy> getVacancies() {
        return jobRepository.getAllVacancies();
    }
}
