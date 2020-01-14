package Launchers;


import Secretary.Secretary;
import org.apache.log4j.BasicConfigurator;

import java.util.Scanner;

public class consoleLauncher extends launcher {
    static private Launchers.launcher launcher;
    private Scanner scanner = new Scanner(System.in);

    private consoleLauncher() {
        launcher = this;
        new Secretary(launcher);
    }

    public static void main(String[] args) {
        BasicConfigurator.configure();
        new consoleLauncher();

    }

    @Override
    public void printMessage(String message) {
        System.out.println(message);
    }

    @Override
    public String getMessage() {
        System.out.println("Введите ответ: ");
        return scanner.nextLine();
    }

    @Override
    public String respond(String message) {
        this.printMessage(message);
        return this.getMessage();
    }

    @Override
    public void restart() {

    }
}
