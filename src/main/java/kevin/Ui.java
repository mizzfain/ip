package kevin;

import java.util.Scanner;

public class Ui {
    private String START_BANNER = "What's up? I'm Kevin, your chatbot for task management.\n\n";
    private String loadingTasksMessage = "";
    private String END_BANNER = "What would you like me to help you with?\n";

    private Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public String readNextLine() {
        return scanner.nextLine();
    }

    public void print(String output) {
        System.out.println(output);
    }

    public void addLoadingTasksMessage(String loadingTasksMessage) {
        this.loadingTasksMessage += loadingTasksMessage;
    }

    public String getInitialMessage() {
        String initialMessage = START_BANNER + loadingTasksMessage;
        if (!loadingTasksMessage.isEmpty()) {
            initialMessage += '\n';
        }
        return(initialMessage + END_BANNER);

    }
    /**
     * Initializes UI and read first input.
     */
    public String start() {
        print(getInitialMessage());
        return readNextLine();
    }
}
