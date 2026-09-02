public class ToDo extends Task {

    public ToDo(String description, boolean isDone) {
        super(description, isDone);
    }

    public ToDo(String description) {
        super(description, false);
    }

    @Override
    public String formatSaveString() {
        return "T | " + super.formatSaveString();
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
