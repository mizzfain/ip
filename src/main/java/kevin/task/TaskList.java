package kevin.task;

import kevin.KevinException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<Task>();
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public Task delete(int taskIndex) throws KevinException {
        try {
            Task task = tasks.get(taskIndex);
            tasks.remove(taskIndex);
            return task;
        } catch (IndexOutOfBoundsException e) {
            throw new KevinException("Must include a valid task number.");
        }
    }

    public Task mark(int taskIndex) throws KevinException {
        try {
            Task markedTask = tasks.get(taskIndex).mark();
            tasks.set(taskIndex, markedTask);
            return markedTask;
        } catch (IndexOutOfBoundsException e) {
            throw new KevinException("Must include a valid task number.");
        }
    }

    public Task unmark(int taskIndex) throws KevinException {
        try {
            Task unmarkedTask = tasks.get(taskIndex).unmark();
            tasks.set(taskIndex, unmarkedTask);
            return unmarkedTask;
        } catch (IndexOutOfBoundsException e) {
            throw new KevinException("Must include a valid task number.");
        }
    }

    public void list() {
        int counter = 1;
        for (Task task : tasks) {
            System.out.println(counter + "." + task);
            counter++;
        }
        System.out.println();
    }

    public int size() {
        return tasks.size();
    }

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
