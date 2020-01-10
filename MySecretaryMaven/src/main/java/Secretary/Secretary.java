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
    launcher launcher;
    private LinkedList<Redirect> redirects = new LinkedList<>();
    private String email = "mixteamlp@gmail.com";
    public Secretary(launcher launcher) {
        this.launcher = launcher;
        redirects.add(new SendEmailSMTP());
        launcher.printMessage("Задайте сообщение для авто ответов");
        replyBot.setReply(launcher.getMessage());
        VkBot bot = new VkBot(VkOauth.getAccessToken(launcher), replyBot, this);
        Thread ListenForNewMessagesThread =new Thread(() -> {
            while (launcher.working){
                try {
                    Thread.sleep(10000);
                    if (userIsHere)VkBot.setAnswerable(launcher);
                    else VkBot.setAnswerable(replyBot);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        ListenForNewMessagesThread.start();

    }

    public void redirect(String message) {
        for (Redirect service : redirects) service.redirectMessage(message, this);
    }

    public String getEmail() {
        return email;
    }
}
