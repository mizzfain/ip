package kevin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import kevin.task.Task;
import kevin.task.TaskList;

/**
 * Storage Class handles file actions.
 * Contains 1 filepath, should always be data/tasks.txt.
 * Able to load tasks from existing file, and save new tasks to create or update file.
 */
public class Storage {
    private Path filePath;

    public Storage(String filePathString) {
        this.filePath = Paths.get(filePathString);
    }

    /**
     * Loads TaskList from tasks.txt.
     * @return TaskList
     */
    /**
     * Loads TaskList from tasks.txt.
     * @return TaskList
     * @throws KevinException If tasks.txt does not exist.
     */
    public TaskList load() throws KevinException {
        TaskList tasks = new TaskList();

        try {
            Path folderPath = filePath.getParent();

            if (folderPath != null) {
                Files.createDirectories(folderPath);
            }

            try (Stream<String> lines = Files.lines(filePath)) {
                lines.map(Task::parseLine)
                        .forEach(tasks::add);
            }
        } catch (IOException e) {
            throw new KevinException(e.getMessage());
        }
        return tasks;
    }

    /**
     * Saves tasks into tasks.txt.
     * @param TaskList tasks
     */
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
