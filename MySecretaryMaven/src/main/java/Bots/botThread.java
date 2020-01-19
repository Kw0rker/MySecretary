package Bots;

import Secretary.Secretary;
import javafx.util.Pair;
import users.user;

import java.util.HashSet;

public class botThread extends java.lang.Thread implements Runnable {
    private int id;
    //VkBot bot;
    private boolean isInterrupted = false;

    public botThread(Secretary secretary, Pair<user, ? extends bot> pair) {
        this.id = pair.getKey().getId();
        ((Runnable) () -> {
            pair.getValue().run(pair.getKey().getAccessToken(), pair.getKey().getId(), secretary.getReplyBot().getInstance(), secretary, this);
        }).run();
    }

    public boolean close(HashSet<botThread> set) {
        this.isInterrupted = true;
        set.remove(this);
        return true;
    }

    @Override
    public boolean isInterrupted() {
        return isInterrupted;
    }

    @Override
    public int hashCode() {
        return id;
    }
}