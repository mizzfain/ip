package kevin.task;

import kevin.KevinException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Class to store Tasks
 */
public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<Task>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds Task to current TaskList.
     */
    public void add(Task task) {
        tasks.add(task);
    }

    public void replace(int taskIndex, Task updatedTask) throws KevinException {
        try {
            tasks.set(taskIndex, updatedTask);
        } catch (IndexOutOfBoundsException e) {
            throw new KevinException("Must include a valid task number.");
        }
    }

    /**
     * Marks Task in current TaskList as done by index.
     * @param taskIndex
     * @return MarkedTask
     * @throws KevinException If index is out of bounds.
     */
    public Task mark(int taskIndex) throws KevinException {
        try {
            Task markedTask = tasks.get(taskIndex).mark();
            this.replace(taskIndex, markedTask);

            return markedTask;
        } catch (IndexOutOfBoundsException e) {
            throw new KevinException("Must include a valid task number.");
        }
    }

    /**
     * Unmarks Task in current TaskList by index.
     * @param taskIndex
     * @return UnmarkedTask
     * @throws KevinException If index is out of bounds.
     */
    public Task unmark(int taskIndex) throws KevinException {
        try {
            Task unmarkedTask = tasks.get(taskIndex).unmark();
            this.replace(taskIndex, unmarkedTask);

            return unmarkedTask;
        } catch (IndexOutOfBoundsException e) {
            throw new KevinException("Must include a valid task number.");
        }
    }

    /**
     * Deletes Task from current TaskList by index.
     * @param taskIndex
     * @return DeletedTask
     * @throws KevinException If index is out of bounds.
     */
    public Task delete(int taskIndex) throws KevinException {
        try {
            Task task = tasks.get(taskIndex);
            tasks.remove(taskIndex);

            return task;
        } catch (IndexOutOfBoundsException e) {
            throw new KevinException("Must include a valid task number.");
        }
    }

    /**
     * Lists all Tasks as a String.
     */
    public String list() {
        String finalString = "";
        int counter = 1;

        for (Task task : tasks) {
            finalString = task.addToList(finalString, counter);
            counter++;
        }
        return finalString;
    }

    /**
     * Returns list of tasks with keyword as a String.
     * @param keyword
     */
    public String find(String keyword) {
        String finalString = "";
        int counter = 1;

        for (Task task : tasks) {
            if (task.contains(keyword)) {
                finalString = task.addToList(finalString, counter);
                counter++;
            }
        }
        return finalString;
    }

    /**
     * Returns number of Tasks in current TaskList.
     */
    public int size() {
        return tasks.size();
    }

    /**

     * Saves TaskList in filePath.
     * Assumes filePath has a parent folder (data).
     * @param filePath
     */
    public void save(Path filePath) {
        //Assumes parent (data) exists and is a folder
        Path folderPath = filePath.getParent();
        assert Files.exists(folderPath) && Files.isDirectory(folderPath);

        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            for (Task task : tasks) {
                String taskString = task.formatSaveString();
                writer.write(taskString);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return tasks.toString();
    }
}
