package job.service;

import job.domain.Vacancy;

import java.util.List;

public class VacancyService {
    private final StorageService storageService;

    public VacancyService(StorageService storageService) {
        this.storageService = storageService;
    }

    public void createVacancy(String name, String company, List<String> tags, int experience) {
        storageService.addVacancy(new Vacancy(name, company, tags, experience));
    }

    public List<Vacancy> getVacancies() {
        return storageService.getVacancies();
    }
}
