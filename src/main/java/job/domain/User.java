package job.domain;

import java.util.List;

public class User {
    private final String name;
    private final List<String> skills;
    private final int experience;


    public User(String name, List<String> skills, int experience) {
        this.name = name;
        this.skills = List.copyOf(skills);
        this.experience = experience;
    }

    @Override
    public String toString() {
        return name + " " + String.join(",", skills) + " " + experience;
    }

    public String getName() {
        return name;
    }
    public List<String> getSkills() {return skills;}
    public double matchScore(Vacancy vacancy) {
        double score = vacancy.countScore(skills);
        if (experience < vacancy.getNeedExperience()) score /= 2;
        return score;
    }
}
