package job.service;

import job.domain.User;
import job.domain.Vacancy;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindBestJob implements Runnable {
    private final UserService userService;
    private final SuggestService suggestService;

    @Scheduled(fixedRate = 5000)
    public void run() {
        for (User user : userService.getUsers()) {
            List<Vacancy> match = suggestService.getMatch(user.getName());
            if (!match.isEmpty()) {
                System.out.println(user.getName() + ": " + match.getFirst().getNameVacancy());
            }
        }
    }
}
