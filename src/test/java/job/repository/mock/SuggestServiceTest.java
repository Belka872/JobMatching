package job.repository.mock;

import job.domain.User;
import job.domain.Vacancy;
import job.repository.JobRepository;
import job.repository.UserRepository;
import job.service.SuggestService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SuggestServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private JobRepository jobRepository;
    @InjectMocks
    private SuggestService suggestService;
    @Test
    void singleVacancyTest() {
        User user = new User(
                "Sasha",
                List.of("Java"),
                2
        );
        Vacancy vacancy = new Vacancy(
                "Java Developer",
                "Example",
                List.of("Java"),
                1
        );
        when(userRepository.getUser("Sasha")).thenReturn(user);
        when(jobRepository.getAllVacancies()).thenReturn(List.of(vacancy));

        List<Vacancy> vacancies = suggestService.getMatch("Sasha");
        assertEquals(List.of(vacancy), vacancies);
    }

    @Test
    void suggestTest() {
        User user = new User(
                "Sasha",
                List.of("Java", "Spring", "SQL"),
                3
        );

        Vacancy bestVacancy = new Vacancy(
                "Java Backend Developer",
                "Alpha",
                List.of("Java", "Spring", "SQL"),
                2
        );

        Vacancy secondVacancy = new Vacancy(
                "Java Developer",
                "Beta",
                List.of("Java", "SQL"),
                3
        );

        Vacancy seniorVacancy = new Vacancy(
                "Senior Java Developer",
                "Gamma",
                List.of("Java", "Spring", "SQL"),
                5
        );

        Vacancy unrelatedVacancy = new Vacancy(
                "Python Developer",
                "Delta",
                List.of("Python", "Django"),
                1
        );

        when(userRepository.getUser("Sasha")).thenReturn(user);
        when(jobRepository.getAllVacancies()).thenReturn(
                List.of(seniorVacancy, unrelatedVacancy, secondVacancy, bestVacancy)
        );
        List<Vacancy> vacancies = suggestService.getMatch("Sasha");
        assertEquals(List.of(bestVacancy, secondVacancy), vacancies);
    }

    @Test
    void emptyVacanciesTest() {
        User user = new User(
                "Sasha",
                List.of("Java", "Spring", "SQL"),
                3
        );
        when(userRepository.getUser("Sasha")).thenReturn(user);
        when(jobRepository.getAllVacancies()).thenReturn(List.of());
        List<Vacancy> vacancies = suggestService.getMatch("Sasha");
        assertEquals(List.of(), vacancies);
    }

    @Test
    void userNotFoundTest() {
        Vacancy bestVacancy = new Vacancy(
                "Java Backend Developer",
                "Alpha",
                List.of("Java", "Spring", "SQL"),
                2
        );
        when(userRepository.getUser("Unknown")).thenThrow(new EmptyResultDataAccessException(1));
        assertThrows(
                EmptyResultDataAccessException.class,
                () -> suggestService.getMatch("Unknown")
        );
    }
}
