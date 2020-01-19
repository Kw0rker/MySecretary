package Launchers;


import Secretary.Secretary;
import org.apache.log4j.BasicConfigurator;
import users.VkBotUser;

import java.util.Scanner;

public class consoleLauncher extends launcher {
    static private Launchers.launcher launcher;
    private Scanner scanner = new Scanner(System.in);
    String token = "7d2ab578133b0612fda8785b22627032c638f4c53422b833b00d06d753ff3111d92bb4d611c263e75c3ca";
    int id = 353087280;

    private consoleLauncher() {
        launcher = this;
        new Secretary(launcher, new VkBotUser("mixteamlp@gmail.com", token, id, "AI"));
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
