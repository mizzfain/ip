package kevin;

import task.Task;
import task.TaskList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Storage {
    private Path filePath;

    public Storage(String filePathString) {
        this.filePath = Paths.get(filePathString);
    }

    public TaskList load() throws KevinException {
        TaskList tasks = new TaskList();

        try {
            Path folderPath = filePath.getParent();

            if (folderPath != null) {
                Files.createDirectories(folderPath);
            }

            try (Stream<String> lines = Files.lines(filePath)) {
                lines.map(Task::fromFormatString)
                        .forEach(tasks::add);
            }
        } catch (IOException e) {
            throw new KevinException(e.getMessage());
        }
        return tasks;
    }

    public void save(TaskList tasks) {
        try {
            Path folderPath = filePath.getParent();

            if (folderPath != null) {
                Files.createDirectories(folderPath);
            }

            tasks.save(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
