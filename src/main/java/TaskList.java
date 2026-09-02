import java.util.ArrayList;

public class TaskList {
    public ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<Task>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public Task delete(int taskIndex) {
        Task task = tasks.get(taskIndex);
        tasks.remove(taskIndex);
        return task;
    }

    public Task mark(int taskIndex) {
        Task markedTask = tasks.get(taskIndex).mark();
        tasks.set(taskIndex, markedTask);
        return markedTask;
    }

    public Task unmark(int taskIndex) {
        Task unmarkedTask = tasks.get(taskIndex).unmark();
        tasks.set(taskIndex, unmarkedTask);
        return unmarkedTask;
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

    @Override
    public String toString() {
        return tasks.toString();
    }
}
