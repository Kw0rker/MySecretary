package interfaces;

import Secretary.Secretary;

public interface bot {
    void run(String key, int id, answerable answerable, Secretary secretary, Thread thread);
    // void run(String key, int id, Secretary secretary, Thread thread);
    void stop();

    void resume();
}
