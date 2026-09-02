package job.service;

import job.domain.User;
import job.domain.Vacancy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatService {
    private final JobService jobService;
    private final UserService userService;
    private final SuggestService suggestService;

    public List<Vacancy> findJobsByExperience(int minExperience) {
        return jobService.getVacancies().stream()
                .filter(vacancy -> vacancy.getNeedExperience() >= minExperience)
                .sorted(Comparator.comparing(Vacancy::getNameVacancy))
                .toList();
    }

    public List<User> findUsersByMatchCount(int minMatches) {
        return userService.getUsers().stream()
                .filter(user -> suggestService.getMatch(user.getName(), false).size() >= minMatches)
                .sorted(Comparator.comparing(User::getName))
                .toList();
    }

    public List<String> getTopSkills(int limit) {
        return userService.getUsers().stream()
                .flatMap(user -> user.getSkills().stream())
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(limit)
                .map(Map.Entry::getKey)
                .sorted()
                .toList();
    }
}
