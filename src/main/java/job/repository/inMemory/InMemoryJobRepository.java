package job.repository.inMemory;

import job.domain.Vacancy;
import job.repository.JobRepository;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
public class InMemoryJobRepository implements JobRepository {
    private final Map<String, Vacancy> vacancies = new LinkedHashMap<>();

    @Override
    public void addVacancy(Vacancy vacancy) {
        vacancies.putIfAbsent(vacancy.getNameVacancy(), vacancy);
    }

    @Override
    public List<Vacancy> getAllVacancies() {
        return List.copyOf(vacancies.values());
    }

    @Override
    public Vacancy getVacancy(String nameVacancy) {
        return vacancies.get(nameVacancy);
    }
}
