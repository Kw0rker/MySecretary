package Bots;

import Secretary.Secretary;
import Vk.VkUser;
import answerable.answerable;
import com.petersamokhin.bots.sdk.clients.User;
import com.petersamokhin.bots.sdk.objects.Message;
import com.petersamokhin.bots.sdk.utils.web.Connection;

public class VkBot extends User  {
    private Secretary secretary;
    private answerable Answerable;
    private String key;
    private int vk_id;
    public VkBot(String key, answerable answerable, Secretary secretary) {
        super(key);
        this.key = key;
        this.secretary = secretary;
        Answerable=answerable;

        onSimpleTextMessage(message -> {
            int id = message.authorId();
            new Message()
                    .from(this)
                    .to(message.authorId())
                    .text(Answerable.respond(message.getText()))
                    .send();
            VkUser user = getUserById(id, key);
            secretary.redirect(message.getText(), user.getFirst_name() + " " + user.getLast_name());
        });
        while (true) {
            try {
                Thread.sleep(10000);
                if (getUserById(vk_id, key).isOnline()) setAnswerable(secretary.launcher);
                else setAnswerable(answerable);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public static VkUser getUserById(int id, String key) {
        String request = "https://api.vk.com/method/users.get?params[user_ids]=" + id + "&params[fields]=online&params[name_case]=Nom&access_token=" + key + "&v=5.103";
        String respond = Connection.getRequestResponse(request);
        try {
            String name = respond.split("\"first_name\":\"")[1].split("\",")[0];
            try {
                String last_name = respond.split("\"last_name\":\"")[1].split("\",")[0];
                /*
                fuck yeah i know how to work w/t json
                help pls
                 */
                return new VkUser(name, last_name, respond.contains("\"online\":1"));
            } catch (Exception e) {
                return new VkUser();
            }
        } catch (Exception e) {
            return new VkUser();
        }
    }

    public void setAnswerable(answerable answerable) {
        Answerable = answerable;
    }

    public void setVkId(int id) {
        this.vk_id = id;
    }
}
