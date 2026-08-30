package job.service;

import job.domain.Match;
import job.domain.User;
import job.domain.Vacancy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MatchService {
    private final StorageService storageService;

    public MatchService(StorageService storageService) {
        this.storageService = storageService;
    }

    public List<Vacancy> getMatch(String nameUser) {
        User user = storageService.getUser(nameUser);
        List<Vacancy> vacancies = storageService.getVacancies();

        List<Match> matches = new ArrayList<>();
        for (Vacancy vacancy : vacancies) {
            double score = user.matchScore(vacancy);
            matches.add(new Match(vacancy, score));
        }
        return matches.stream()
                .sorted(Comparator.comparingDouble(Match::score).reversed())
                .limit(2)
                .map(Match::vacancy)
                .toList();
    }
}
