package job.service;

import job.domain.Match;
import job.domain.User;
import job.domain.Vacancy;
import job.repository.JobRepository;
import job.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;
@Service
@RequiredArgsConstructor
public class SuggestService {
    private final UserRepository userRepository;
    private final JobRepository jobRepository;

    public List<Vacancy> getMatch(String userName) {
        return getMatch(userName, true);
    }

    public List<Vacancy> getMatch(String userName, boolean needLimit) {
        User user = userRepository.getUser(userName);

        Stream<Match> matches = jobRepository.getAllVacancies().stream()
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
