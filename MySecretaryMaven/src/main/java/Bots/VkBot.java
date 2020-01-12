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
    private static String key = "1016899c1016899c1016899cf110786606110161016899c4e07feafbe57356a264ebd63";
    private int vk_id;

    public VkBot(String key, int vk_id, answerable answerable, Secretary secretary) {
        super(key);
        VkBot.key = key;
        this.vk_id = vk_id;
        this.secretary = secretary;
        Answerable=answerable;

        onSimpleTextMessage(message -> {
            int id = message.authorId();
            new Message()
                    .from(this)
                    .to(message.authorId())
                    .text(Answerable.respond(message.getText()))
                    .send();
            VkUser user = getUserById(id);
            if (!getUserById(vk_id).isOnline())
                secretary.redirect(message.getText(), user.getFirst_name() + " " + user.getLast_name());
        });
        while (true) {
            try {
                Thread.sleep(10000);
                if (getUserById(vk_id).isOnline()) setAnswerable(secretary.launcher);
                else setAnswerable(answerable);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public static VkUser getUserById(int id) {
        String request = "https://api.vk.com/method/users.get?access_token=1016899c1016899c1016899cf110786606110161016899c4e07feafbe57356a264ebd63&user_id=" + id + "&fields=online&v=5.00";
        String respond = Connection.getRequestResponse(request);
        System.out.println(id);
        System.out.println(respond);
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

}
