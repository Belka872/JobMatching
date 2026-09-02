package job.service;

import job.domain.User;
import job.domain.Vacancy;

import java.util.Comparator;
import java.util.List;

public class FindBestJob implements Runnable {
    private final UserService userService;
    private final MatchService matchService;

    public FindBestJob(UserService userService, MatchService matchService) {
        this.userService = userService;
        this.matchService = matchService;
    }

    public void run() {
        for (User user : userService.getUsers()) {
            List<Vacancy> match = matchService.getMatch(user.getName());
            if (!match.isEmpty()) {
                System.out.println(user.getName() + ": " + match.getFirst().getNameVacancy());
            }
        }
    }
}
