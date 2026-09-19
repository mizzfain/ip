package kevin;

import java.util.Scanner;

public class Ui {
    private String START_BANNER = "Hello! I'm Kevin.\n\n";
    private String loadingBanner = "";
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
        loadingBanner += loadingTasksMessage;
    }

    /**
     * Initializes UI and read first input.
     */
    public String start() {
        print(START_BANNER + loadingBanner + '\n' + END_BANNER);
        return readNextLine();
    }
}
