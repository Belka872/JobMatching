package job.service;

import job.domain.User;
import job.domain.Vacancy;

import java.util.ArrayList;
import java.util.List;

public class StorageService {
    private final List<User> users = new ArrayList<>();
    private final List<Vacancy> vacancies = new ArrayList<>();

    public void addUser(User user) {
        if (getUser(user.getName()) == null) users.add(user);
    }

    public void addVacancy(Vacancy vacancy) {
        if (getVacancy(vacancy.getNameVacancy()) == null) vacancies.add(vacancy);
    }

    public List<User> getUsers() {
        return List.copyOf(users);
    }

    public List<Vacancy> getVacancies() {
        return List.copyOf(vacancies);
    }

    public User getUser(String name) {
        for (User user : users) {
            if (user.getName().equals(name)) {
                return user;
            }
        }
        return null;
    }

    public Vacancy getVacancy(String nameVacancy) {
        for (Vacancy vacancy : vacancies) {
            if (vacancy.getNameVacancy().equals(nameVacancy)) {
                return vacancy;
            }
        }
        return null;
    }
}