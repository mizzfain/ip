import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Storage {
    private Path filePath;

    public Storage(String filePathString) {
        this.filePath = Paths.get(filePathString);
    }

    public TaskList load() {

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
