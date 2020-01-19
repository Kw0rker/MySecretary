package users;

import com.sun.istack.internal.NotNull;

public class user {
    private String email;
    @NotNull
    private String accessToken;
    @NotNull
    private int id;
    @NotNull
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
