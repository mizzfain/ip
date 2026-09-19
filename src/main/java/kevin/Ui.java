package kevin;

import java.util.Scanner;

public class Ui {
    private static final String START_BANNER =
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

    /**
     * Initialises UI and read
     * @return
     */
    public String start() {
        print(START_BANNER);
        return readNextLine();
    }
}
