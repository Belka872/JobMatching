package job.service;

import java.util.List;
import java.util.Map;

public record ParsedCommand(String name, List<String> arguments, Map<String, String> options) {
}
