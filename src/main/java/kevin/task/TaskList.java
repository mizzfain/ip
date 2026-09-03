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
    public ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<Task>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Add Task to current TaskList.
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Delete Task from current TaskList by index.
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
     * Mark Task in current TaskList as done by index.
     * @param taskIndex
     * @return MarkedTask
     * @throws KevinException If index is out of bounds.
     */
    public Task mark(int taskIndex) throws KevinException {
        try {
            Task markedTask = tasks.get(taskIndex).mark();
            tasks.set(taskIndex, markedTask);
            return markedTask;
        } catch (IndexOutOfBoundsException e) {
            throw new KevinException("Must include a valid task number.");
        }
    }

    /**
     * Unmark Task in current TaskList by index.
     * @param taskIndex
     * @return UnmarkedTask
     * @throws KevinException If index is out of bounds.
     */
    public Task unmark(int taskIndex) throws KevinException {
        try {
            Task unmarkedTask = tasks.get(taskIndex).unmark();
            tasks.set(taskIndex, unmarkedTask);
            return unmarkedTask;
        } catch (IndexOutOfBoundsException e) {
            throw new KevinException("Must include a valid task number.");
        }
    }

    /**
     * Lists all Tasks.
     */
    public void list() {
        int counter = 1;
        for (Task task : tasks) {
            System.out.println(counter + "." + task);
            counter++;
        }
        System.out.println();
    }

    /**
     * Returns number of Tasks in current TaskList.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Saves TaskList into tasks.txt.
     * @param filePath
     */
    public void save(Path filePath) {
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
