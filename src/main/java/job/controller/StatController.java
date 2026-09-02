package job.controller;

import job.domain.User;
import job.domain.Vacancy;
import job.service.StatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/stats")
@RequiredArgsConstructor
public class StatController {
    private final StatService statService;

    @GetMapping("/jobs")
    public List<Vacancy> jobs(
            @RequestParam int minExperience
    ) {
        return statService.findJobsByExperience(minExperience);
    }

    @GetMapping("/users")
    public List<User> users(
            @RequestParam int minMatches
    ) {
        return statService.findUsersByMatchCount(minMatches);
    }

    @GetMapping("/top-skills")
    public List<String> topSkills(
            @RequestParam int limit
    ) {
        return statService.getTopSkills(limit);
    }
}