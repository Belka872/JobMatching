package job.service;

import job.CLI.CommandParser;
import job.domain.ParsedCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CommandService {
    private final SuggestService suggestService;
    private final FileService fileService;
    private final UserService userService;
    private final JobService jobService;
    private final StatService statService;

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

            case "job" -> jobService.createVacancy(
                    command.arguments().getFirst(),
                    command.options().get("company"),
                    parseList(command.options().get("tags")),
                    Integer.parseInt(command.options().get("exp"))
            );

            case "job-list" -> jobService.getVacancies()
                    .forEach(System.out::println);

            case "suggest" -> suggestService.getMatch(command.arguments().getFirst())
                    .forEach(System.out::println);

            case "history" -> fileService.getHistory()
                    .forEach(System.out::println);

            case "stat" -> {
                if (command.options().containsKey("exp")) {
                    int minExperience = Integer.parseInt(command.options().get("exp"));
                    statService.findJobsByExperience(minExperience).forEach(System.out::println);
                } else if (command.options().containsKey("match")) {
                    int minMatches = Integer.parseInt(command.options().get("match"));
                    statService.findUsersByMatchCount(minMatches).forEach(System.out::println);
                } else if (command.options().containsKey("top-skills")) {
                    int limit = Integer.parseInt(command.options().get("top-skills"));
                    statService.getTopSkills(limit).forEach(System.out::println);
                }
            }


            case "exit" -> System.exit(0);
        }
    }

    private List<String> parseList(String value) {
        return Arrays.asList(value.split(","));
    }
}
