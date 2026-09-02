package job.repository;

import job.domain.Vacancy;

import java.util.List;

public interface JobRepository {
    void addVacancy(Vacancy vacancy);

    List<Vacancy> getAllVacancies();

    Vacancy getVacancy(String nameVacancy);
}
