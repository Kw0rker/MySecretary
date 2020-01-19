package Bots;

import Secretary.Secretary;
import interfaces.answerable;

public interface bot {
    void run(String key, int id, answerable answerable, Secretary secretary, Thread thread);
}
