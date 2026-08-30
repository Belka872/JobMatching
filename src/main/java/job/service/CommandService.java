package job.service;

import job.domain.User;
import job.domain.Vacancy;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CommandService {
    private final MatchService matchService;
    private final FileService fileService;
    private final UserService userService;
    private final VacancyService vacancyService;

    public CommandService(MatchService matchService, FileService fileService, UserService userService, VacancyService vacancyService) {
        this.matchService = matchService;
        this.fileService = fileService;
        this.userService = userService;
        this.vacancyService = vacancyService;
    }

    public void processCommand() {
        List<String> history = fileService.getHistory();
        for (String input : history) {
            ParsedCommand command = CommandParser.parse(input);
            if (command.name().equals("user") || command.name().equals("job")) {
                execute(command);
            }
        }
    }

    public void handle(String input) {
        ParsedCommand command = CommandParser.parse(input);
        execute(command);
        fileService.saveCommand(input);
    }

    private void execute(ParsedCommand command) {
        switch (command.name()) {
            case "user" -> userService.createUser(
                    command.arguments().getFirst(),
                    parseList(command.options().get("skills")),
                    Integer.parseInt(command.options().get("exp"))
            );

            case "user-list" -> userService.getUsers().forEach(System.out::println);

            case "job" -> vacancyService.createVacancy(
                    command.arguments().getFirst(),
                    command.options().get("company"),
                    parseList(command.options().get("tags")),
                    Integer.parseInt(command.options().get("exp"))
            );

            case "job-list" -> vacancyService.getVacancies()
                    .forEach(System.out::println);

            case "suggest" -> matchService.getMatch(command.arguments().getFirst())
                    .forEach(System.out::println);

            case "history" -> fileService.getHistory()
                    .forEach(System.out::println);

            case "stat" -> {
                if (command.options().containsKey("exp")) {
                    vacancyService.getVacancies().stream()
                            .filter(s -> s.getNeedExperience() >= Integer.parseInt(command.options().get("exp")))
                            .sorted(Comparator.comparing(Vacancy::getNameVacancy))
                            .forEach(System.out::println);
                } else if (command.options().containsKey("match")) {
                    int minMatches = Integer.parseInt(command.options().get("match"));

                    userService.getUsers().stream()
                            .filter(user -> matchService.getMatch(user.getName(), false).size() >= minMatches)
                            .sorted(Comparator.comparing(User::getName))
                            .forEach(System.out::println);
                } else if (command.options().containsKey("top-skills")) {
                    int top = Integer.parseInt(command.options().get("top-skills"));

                    userService.getUsers().stream()
                            .flatMap(user -> user.getSkills().stream())
                            .collect(Collectors.groupingBy(
                                    Function.identity(),
                                    Collectors.counting()
                            ))
                            .entrySet().stream()
                            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                            .limit(top)
                            .map(Map.Entry::getKey)
                            .sorted()
                            .forEach(System.out::println);
                }
            }


            case "exit" -> System.exit(0);
        }
    }

    private List<String> parseList(String value) {
        return Arrays.stream(value.split(","))
                .distinct()
                .sorted()
                .toList();
    }
}
