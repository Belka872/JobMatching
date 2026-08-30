package job.domain;

import java.util.List;

public class Vacancy {
    private final String nameVacancy;
    private final String nameCompany;
    private final List<String> tags;
    private final int needExperience;

    public Vacancy(String nameVacancy, String nameCompany, List<String> tags, int needExperience) {
        this.nameVacancy = nameVacancy;
        this.nameCompany = nameCompany;
        this.tags = List.copyOf(tags);
        this.needExperience = needExperience;
    }

    @Override
    public String toString() {
        return String.format("%s at %s", nameVacancy, nameCompany);
    }

    public String getNameVacancy() {
        return nameVacancy;
    }

    public int getNeedExperience() {
        return needExperience;
    }

    public int countScore(List<String> skills) {
        int score = 0;
        for (String skill : skills) {
            if (tags.contains(skill)) score++;
        }
        return score;
    }
}
