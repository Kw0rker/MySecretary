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
    private HashSet<botThread> threads = new HashSet<>();
    DataBase dataBase;

    public Secretary(launcher launcher) {

        this.launcher = launcher;
        dataBase = new DataBase();
        // this.user = user;
        redirectsServices.add(new SendEmailSMTP());
        //replyBot.setReply(user.getReply());
        setup();
        VkBotParser parser = new VkBotParser(this);
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
            VkBot bot = new VkBot(user.getAccessToken(), user.getId(), replyBot.getInstance(), this);
            Pair<user, bot> pair = new Pair<>(user, bot);
            threads.add(new botThread(this, pair));
        }
    }

    public void stopUser(int id) {
        for (botThread thread : threads) {
            if (thread.getID() == id) thread.interrupt();
        }
    }

    public void resumeUser(int id) {
        for (botThread thread : threads) {
            if (thread.getID() == id) thread.Resume();
        }
    }

    public void changeReply(String reply, int id) {
        for (botThread thread : threads) {
            if (thread.getID() == id) thread.getBot().setReply(reply);
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
