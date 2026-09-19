package job.repository.integraion;

import job.domain.User;
import job.domain.Vacancy;
import job.repository.JobRepository;
import job.repository.UserRepository;
import job.service.SuggestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

import java.util.List;

@SpringBootTest(properties = "jobmatch.cli.enabled=false")
@Testcontainers
class SuggestServiceIntegrationTest {

    @Container
    static final PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:17-alpine")
            .withDatabaseName("jobmatch_test")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void configureDatabase(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private SuggestService suggestService;

    @Test
    void suggestTest() {
        User sasha = new User(
                "Sasha",
                List.of("Java", "Spring", "SQL"),
                3
        );

        User masha = new User(
                "Masha",
                List.of("Python", "Django"),
                2
        );

        userRepository.addUser(sasha);
        userRepository.addUser(masha);

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

        Vacancy pythonVacancy = new Vacancy(
                "Python Developer",
                "Delta",
                List.of("Python", "Django"),
                1
        );

        jobRepository.addVacancy(bestVacancy);
        jobRepository.addVacancy(secondVacancy);
        jobRepository.addVacancy(seniorVacancy);
        jobRepository.addVacancy(pythonVacancy);

        List<Vacancy> result = suggestService.getMatch("Sasha");
        List<String> resultNames = result.stream().map(Vacancy::getNameVacancy).toList();
        assertEquals(
                List.of("Java Backend Developer", "Java Developer"),
                resultNames
        );
    }
}
