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
    private static Secretary instance;

    public Secretary(launcher launcher) {
        instance = this;
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

    public void redirect(String message, String name) {
        for (Redirect service : redirectsServices) service.redirectMessage(message, name, this);
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
            Runnable runnable = new Runnable() {
                public void run() {
                    VkBot bot = new VkBot(user.getAccessToken(), user.getId(), replyBot.getInstance(), instance);
                    Pair<user, bot> pair = new Pair<>(user, bot);
                    botThread thread = new botThread(instance, pair);
                    System.out.println("created thread with id\n" + thread.getID());
                    threads.add(thread);
                }
            };
            new Thread(runnable).start();
            System.out.println("user added");
        }
    }

    public void stopUser(int id) {
        System.out.println("stop user with id:" + id);
        for (botThread thread : threads) {
            if (thread.getID() == id) {
                thread.interrupt();
                System.out.println("thread stopped with id:" + id);
            }
        }
    }

    public void resumeUser(int id) {
        System.out.println("resume user");
        for (botThread thread : threads) {
            if (thread.getID() == id) thread.Resume();
        }
    }

    public void deleteUser(int id) {
        try {
            dataBase.deleteUser(id);
            for (botThread thread : threads) {
                if (thread.getID() == id) {
                    thread.close(threads);
                    System.out.println(thread.getID() + ":found");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
    }

    public void changeReply(String reply, int id) {
        System.out.println(threads.size());
        try {
            dataBase.changeReply(reply, id);

        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
        for (botThread thread : threads) {
            if (thread.getID() == id) {
                System.out.println("found");
                thread.getBot().setReply(reply);

            }
        }

    }

    public void addNewUser(Pair<users.user, ? extends interfaces.bot> pair) {
        try {
            dataBase.insert((VkBotUser) pair.getKey());
        } catch (SQLException e) {
            return;//If any sql exception occurs dont create a new thread also if user is already in db
        }
        this.threads.add(new botThread(this, pair));
    }
}
