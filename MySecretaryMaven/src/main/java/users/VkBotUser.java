package users;

import java.beans.ConstructorProperties;

public class VkBotUser extends user {

    @ConstructorProperties({"email", "user Access Toke", "Vk id", "auto reply message"})
    public VkBotUser(String email, String accessToken, int id, String reply, boolean onPause) {
        super(email, accessToken, id, reply, onPause);
    }
}
