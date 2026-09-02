public class ToDo extends Task {

    public ToDo(String description) {
        super(description);
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
