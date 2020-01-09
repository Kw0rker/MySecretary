package Launchers;

import answerable.answerable;

public abstract class launcher implements answerable {
    public boolean working =true;
    public abstract void printMessage(String message);
    public abstract String getMessage();
}
