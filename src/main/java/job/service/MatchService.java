package job.service;

import job.domain.Match;
import job.domain.User;
import job.domain.Vacancy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class MatchService {
    private final StorageService storageService;

    public MatchService(StorageService storageService) {
        this.storageService = storageService;
    }

    public List<Vacancy> getMatch(String userName) {
        return getMatch(userName, true);
    }

    public List<Vacancy> getMatch(String userName, boolean needLimit) {
        User user = storageService.getUser(userName);

        Stream<Match> matches = storageService.getVacancies().stream()
                .map(vacancy -> new Match(vacancy, user.matchScore(vacancy)))
                .filter(match -> match.score() > 0)
                .sorted(Comparator.comparingDouble(Match::score).reversed());

        if (needLimit) {
            matches = matches.limit(2);
        }

        return matches
                .map(Match::vacancy)
                .toList();
    }
}
