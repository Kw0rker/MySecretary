package Secretary;

import Bots.ReplyBot;
import Bots.VkBot;
import Bots.botThread;
import Launchers.launcher;
import Vk.VkBotParser;
import interfaces.Redirect;
import javafx.util.Pair;
import users.VkBotUser;
import util.SendEmailSMTP;

import java.io.FileWriter;
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
    FileWriter writer;

    public Secretary(launcher launcher) {

        this.launcher = launcher;
        // this.user = user;
        redirectsServices.add(new SendEmailSMTP());
        //replyBot.setReply(user.getReply());
        VkBotParser parser = new VkBotParser(this);
        //threads.add(new botThread(this,user));

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
        /*
        goes through users list and setup a vk bot for them
         */
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

    public void addNewUser(Pair<users.user, ? extends interfaces.bot> pair) {
        boolean contains = false;
        for (botThread thread : threads) if (thread.getID() == user.getId()) contains = true;
        if (!contains) this.threads.add(new botThread(this, pair));
    }
}
