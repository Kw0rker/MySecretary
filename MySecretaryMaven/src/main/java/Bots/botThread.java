package Bots;

import Secretary.Secretary;
import interfaces.bot;
import javafx.util.Pair;
import users.user;
import util.ExceptionHandler;

import java.util.HashSet;

public class botThread extends Thread implements Runnable {
    private int id;
    //VkBot bot;
    private boolean isInterrupted = false;
    private Pair<user, ? extends bot> pair;
    private Secretary secretary;

    public botThread(Secretary secretary, Pair<user, ? extends bot> pair) {
        super(() -> pair.getValue().run(pair.getKey().getAccessToken(), pair.getKey().getId(), pair.getValue().getReply().getInstance(), secretary, pair.getValue()), pair.getKey().getId() + "");
        this.id = pair.getKey().getId();
        this.setUncaughtExceptionHandler(new ExceptionHandler(secretary));
        this.secretary = secretary;
        System.out.println("thread created with id:" + this.id);
        this.pair = pair;
        start();
    }

    public boolean close(HashSet<botThread> set) {
        this.isInterrupted = true;
        if (pair.getValue() instanceof VkBot) {
            VkBot bot = (VkBot) pair.getValue();
            bot.longPoll().off();
        }
        interrupt();
        set.remove(this);
        return true;
    }

    public boolean isInterrupted() {
        return isInterrupted;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public void interrupt() {
        isInterrupted = true;
        pair.getValue().stop();
    }

    public void Resume() {
        isInterrupted = false;
        pair.getValue().resume();
    }

    public int getID() {
        return this.id;
    }

    public bot getBot() {
        return pair.getValue();
    }

    /*@Override
    public void run() {
        pair.getValue().run(pair.getKey().getAccessToken(), pair.getKey().getId(), pair.getValue().getReply().getInstance(), secretary, pair.getValue());


    }*/
}
