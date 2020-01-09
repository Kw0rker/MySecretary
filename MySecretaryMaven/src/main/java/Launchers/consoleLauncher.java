package Launchers;


import Secretary.Secretary;

import java.util.Scanner;

public class consoleLauncher extends launcher {
    static Launchers.launcher launcher;
    Scanner scanner=new Scanner(System.in);
    public static void main(String[] args) {
        new consoleLauncher();

        new Secretary(launcher);
    }

    public consoleLauncher() {
        launcher=this;
    }

    @Override
    public void printMessage(String message) {
        System.out.println(message);
    }

    @Override
    public String getMessage() {
        System.out.println("Введите ответ: ");
        return scanner.next();
    }

    @Override
    public String respond(String message) {
        this.printMessage(message);
        return this.getMessage();
    }
}
