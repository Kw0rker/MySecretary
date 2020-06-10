package Secretary;

import Bots.ReplyBot;
import Bots.VkBot;
import Bots.botThread;
import Launchers.launcher;
import Vk.VkBotParser;
import interfaces.Redirect;
import interfaces.bot;
import javafx.util.Pair;
import users.VkBotUser;
import users.user;
import util.DataBase;
import util.ExceptionHandler;
import util.SendEmailSMTP;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedList;

public class Secretary {
    private static ReplyBot replyBot=new ReplyBot();
    public launcher launcher;
    public static final short numberOfBots = 1;
    private VkBot bot;
    private LinkedList<Redirect> redirectsServices = new LinkedList<>();
    private VkBotUser user;
    private final HashSet<botThread> threads = new HashSet<>();
    DataBase dataBase;
    Pair<user, bot> currentPair;
    private static Secretary instance;

    public Secretary(launcher launcher) {
        instance = this;
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        this.launcher = launcher;
        new VkBotParser(this);
        dataBase = new DataBase();
        // this.user = user;
        redirectsServices.add(new SendEmailSMTP());
        //replyBot.setReply(user.getReply());
        setup();
        System.out.println("setup done");

        //threads.add(new botThread(this,user));
        //setup();

    }

    public void redirect(String message, String name, String email) {
        System.out.println(message);
        for (Redirect service : redirectsServices) service.redirectMessage(message, name, this, email);
    }

    public String getEmail() {
        return user.getEmail();
    }

    public VkBot getBot() {
        return bot;
    }

    public ReplyBot getReplyBot() {
        return replyBot;
    }

    private void setup() {
        HashSet<VkBotUser> users = dataBase.getAllUsers();
        for (users.user user : users) {
            ReplyBot replyBot = new ReplyBot();
            replyBot.setReply(user.getReply());
            Pair<user, bot> pair;
            Thread thread1 = new Thread(() -> {
                VkBot bot = new VkBot(instance, user, replyBot.getInstance());
                bot.setup();
                threads.add(new botThread(instance, new Pair<>(user, bot)));
            });
            thread1.start();
            System.out.println("Thread started");

        }
    }

    public void stopUser(int id) {
        try {
            dataBase.pauseUser(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
        for (botThread thread : threads) {
            if (thread.getID() == id) {

                thread.getBot().stop();
            }
        }
    }

    public void resumeUser(int id) {
        try {
            dataBase.resumeUser(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
        for (botThread thread : threads) {
            if (thread.getID() == id) {
                thread.Resume();

            }
        }
    }

    public void deleteUser(int id) {
        try {
            dataBase.deleteUser(id);
            for (botThread thread : threads) {
                if (thread.getID() == id) {
                    thread.close(threads);
                }
            }
        } catch (SQLException e) {
            ExceptionHandler.logException(Thread.currentThread(), e);
            return;
        }
    }

    public void changeReply(String reply, int id) {
        reply = reply.replace("<br>", "\n");
        System.out.println(threads.size());
        try {
            dataBase.changeReply(reply, id);

        } catch (SQLException e) {
            ExceptionHandler.logException(Thread.currentThread(), e);
            return;
        }
        for (botThread thread : threads) {
            if (thread.getID() == id) {
                thread.getBot().setReply(reply);

            }
        }

    }

    public void addNewUser(Pair<users.user, ? extends interfaces.bot> pair) {
        try {
            dataBase.insert((VkBotUser) pair.getKey());
        } catch (SQLException e) {
            ExceptionHandler.logException(Thread.currentThread(), e);
            return;//If any sql exception occurs dont create a new thread also if user is already in db
        }
        this.threads.add(new botThread(this, pair));
    }
}
