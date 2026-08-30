package job.service;

import java.util.*;

public class CommandParser {
    public static ParsedCommand parse(String input) {
        String[] args = input.trim().split("\\s+");
        String name = args[0];
        List<String> argumenst = new ArrayList<>();
        Map<String, String> options = new HashMap<>();
        for (int i = 1; i < args.length; i++) {
            if (args[i].startsWith("--")) {
                String token = args[i];
                int separator = token.indexOf('=');

                String optionName = token.substring(2, separator);
                String optionValue = token.substring(separator + 1);

                options.put(optionName, optionValue);
            } else {
                argumenst.add(args[i]);
            }
        }
        return new ParsedCommand(name, argumenst, options);
    }
}
