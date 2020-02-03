package Bots;

import Secretary.Secretary;
import Vk.VkUser;
import com.petersamokhin.bots.sdk.clients.User;
import com.petersamokhin.bots.sdk.objects.Message;
import com.petersamokhin.bots.sdk.utils.web.Connection;
import interfaces.answerable;
import interfaces.bot;

public class VkBot extends User implements bot {
    private Secretary secretary;
    private answerable Answerable;
    private answerable replyBot;
    boolean interrupted = false;
    //private static String key = "1016899c1016899c1016899cf110786606110161016899c4e07feafbe57356a264ebd63";
    private int id;

    public VkBot(String key, int id, answerable answerable, Secretary secretary) {
        super(key);
        //VkBot.key = key;
        replyBot = answerable;
        this.id = id;
        this.secretary = secretary;

        Answerable=answerable;
        System.out.println(Answerable.getClass().toString());
    }

    public static VkUser getUserById(int id) {
        String request = "https://api.vk.com/method/users.get?access_token=1016899c1016899c1016899cf110786606110161016899c4e07feafbe57356a264ebd63&user_id=" + id + "&fields=online&v=5.00";
        String respond = Connection.getRequestResponse(request);
        System.out.println(id);
        System.out.println(respond);
        System.gc();
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

    @Override
    public void run(String key, int id, answerable answerable, Secretary secretary, Thread thread) {
        onSimpleTextMessage(message -> {
            System.out.println(message);
            int ID = message.authorId();
            String reply = Answerable.respond(message.getText());
            System.out.println("Reply: " + reply);
            while (reply == null || reply.equals("")) {
                Answerable.restart();
                reply = Answerable.respond(message.getText());
            }
            VkUser user = getUserById(ID);
            if (!user.isOnline())
                secretary.redirect(message.getText(), user.getFirst_name() + " " + user.getLast_name());
            new Message()
                    .from(this)
                    .to(message.authorId())
                    .text(reply)
                    .send();

        });
        onPhotoMessage(message -> new Message()
                .from(this)
                .to(message.authorId())
                .text("К сожалению функция распознавания фото еще не введена\n Но вы можете помочь мне тут-\nhttps://github.com/Kw0rker/MySecretary")
                .send());
        checkUserOnline(id, secretary, thread);
    }

    @Override
    public void stop() {
        Answerable = secretary.launcher;
        interrupted = true;
    }

    @Override
    public void resume() {
        Answerable = replyBot;
        interrupted = false;
    }

    private void checkUserOnline(int id, Secretary secretary, Thread thread) {
        //if (!thread.isAlive()) return;
        while (!interrupted) {
            try {
                Thread.sleep(10000);
                System.gc();
                if (getUserById(id).isOnline()) setAnswerable(secretary.launcher);
                else setAnswerable(replyBot.getInstance());
                System.out.println(Answerable.getClass().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        checkUserOnline(id, secretary, thread);
    }

    @Override
    public answerable getReply() {
        return Answerable;
    }

    @Override
    public void setReply(String reply) {
        this.Answerable.setReply(reply);

    }
}
