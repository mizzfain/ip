package kevin;

import java.util.Scanner;

public class Ui {
    private String startBanner =
            "Hello! I'm Kevin.\nWhat would you like me to help you with?\n";

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
        startBanner += loadingTasksMessage;
    }

    /**
     * Initialises UI and read
     * @return
     */
    public String start() {
        print(startBanner);
        return readNextLine();
    }
}
