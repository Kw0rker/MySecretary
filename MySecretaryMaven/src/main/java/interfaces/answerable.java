package interfaces;


public interface answerable {
    String respond(String message);

    void setReply(String message);
    void restart();

    answerable getInstance();
}
