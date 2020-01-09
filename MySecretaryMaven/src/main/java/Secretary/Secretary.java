package Secretary;

import Bots.ReplyBot;
import Bots.VkBot;
import Launchers.launcher;
import Vk.VkOauth;
import answerable.answerable;

public class Secretary {
    private boolean userIsHere=false;
    private static ReplyBot replyBot=new ReplyBot();
    launcher launcher;
    public Secretary(launcher launcher) {
        this.launcher = launcher;
        VkBot bot=new VkBot(VkOauth.getAccessToken(launcher),replyBot);
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

}
