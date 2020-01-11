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
    public Secretary(launcher launcher) {
        this.launcher = launcher;
        redirects.add(new SendEmailSMTP());
        launcher.printMessage("Задайте сообщение для авто ответов");
        replyBot.setReply(launcher.getMessage());
        VkBot bot = new VkBot(VkOauth.getAccessToken(launcher), replyBot, this);

    }

    public void redirect(String message, String name) {
        for (Redirect service : redirects) service.redirectMessage(message, name, this);
    }

    public String getEmail() {
        return email;
    }
}
