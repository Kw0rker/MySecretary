package Secretary;

import Bots.ReplyBot;
import Bots.VkBot;
import Launchers.launcher;
import Vk.VkOauth;
import answerable.Redirect;
import util.SendEmailSMTP;

import java.util.LinkedList;

public class Secretary {
    private boolean userIsHere=false;
    private static ReplyBot replyBot=new ReplyBot();
    static boolean working;
    public launcher launcher;
    private LinkedList<Redirect> redirects = new LinkedList<>();
    private String email = "mixteamlp@gmail.com";
    private VkBot bot;
    public Secretary(launcher launcher) {
        this.launcher = launcher;
        redirects.add(new SendEmailSMTP());
        launcher.printMessage("Задайте сообщение для авто ответов");
        replyBot.setReply(launcher.getMessage());
        VkOauth vkOauth = new VkOauth();
        String token = vkOauth.getAccessToken(launcher);
        bot = new VkBot(token, vkOauth.getId(), replyBot.getInstance(), this);

    }

    public void redirect(String message, String name) {
        for (Redirect service : redirects) service.redirectMessage(message, name, this);
    }

    public String getEmail() {
        return email;
    }

    public VkBot getBot() {
        return bot;
    }
}
