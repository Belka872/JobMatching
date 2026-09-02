package job.controller;

import job.domain.Vacancy;
import job.dto.CreateJobRequest;
import job.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/job")
@RequiredArgsConstructor
public class JobController {
    private final JobService jobService;

    @PostMapping
    public void create(@RequestBody CreateJobRequest request) {
        jobService.createVacancy(request.nameVacancy(), request.nameCompany(), request.tags(), request.needExperience());
    }

    @GetMapping
    public List<Vacancy> getVacancies() {
        return jobService.getVacancies();
    }
}
