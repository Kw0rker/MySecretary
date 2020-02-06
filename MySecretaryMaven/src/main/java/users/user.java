package users;


public class user {
    private String email;
    private String accessToken;
    private int id;
    private String reply;

    public user(String email, String accessToken, int id, String reply) {
        this.email = email;
        this.accessToken = accessToken;
        this.id = id;
        this.reply = reply;
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
}
