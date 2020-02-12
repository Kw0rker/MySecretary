package users;


public class user {
    private String email;
    private String accessToken;
    private int id;
    private String reply;
    private boolean onPause;

    public user(String email, String accessToken, int id, String reply, boolean onPause) {
        this.email = email;
        this.accessToken = accessToken;
        this.id = id;
        this.reply = reply;
        this.onPause = onPause;
    }

    public String getEmail() {
        return email;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public int getId() {
        return id;
    }

    public String getReply() {
        return reply;
    }

    public boolean isOnPause() {
        return onPause;
    }

    public void setOnPause(boolean onPause) {
        this.onPause = onPause;
    }
}
