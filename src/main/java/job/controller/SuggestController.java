package job.controller;


import job.domain.Vacancy;
import job.service.SuggestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/suggest")
@RequiredArgsConstructor
public class SuggestController {
    private final SuggestService suggestService;

    @GetMapping("/{username}")
    public List<Vacancy> getMatch(@PathVariable String username) {
        return suggestService.getMatch(username);
    }
}
