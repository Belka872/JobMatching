package job.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class FileService {
    private final Path historyPath;

    public FileService(String historyPath) {
        this.historyPath = Path.of(historyPath);
    }

    public void saveCommand(String command) {
        try {
            Files.writeString(historyPath, command + System.lineSeparator(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getHistory() {
        if (Files.notExists(historyPath)) {
            return List.of();
        }
        try {
            return Files.readAllLines(historyPath, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return List.of();
    }
}
