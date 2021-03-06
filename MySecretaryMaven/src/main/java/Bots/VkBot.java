package Bots;

import Secretary.Secretary;
import Vk.VkUser;
import com.petersamokhin.bots.sdk.clients.User;
import com.petersamokhin.bots.sdk.objects.Message;
import com.petersamokhin.bots.sdk.utils.web.Connection;
import interfaces.answerable;
import interfaces.bot;
import users.user;
import util.ExceptionHandler;

public class VkBot extends User implements bot {
    private Secretary secretary;
    private answerable Answerable;
    private answerable replyBot;
    boolean interrupted = false;
    String key;
    //private static String key = "1016899c1016899c1016899cf110786606110161016899c4e07feafbe57356a264ebd63";
    private int vk_id;
    private String email;
    private user user;
    //private int author_id;

    public VkBot(Secretary secretary, user user, answerable answerable) {
        super(user.getId(), user.getAccessToken());
        this.email = email;
        this.user = user;
        enableLoggingUpdates(false);
        this.key = key;
        this.vk_id = user.getId();
        this.secretary = secretary;
        if (user.isOnPause()) {
            Answerable = secretary.launcher;
            System.out.println("user on pause");
        } else Answerable = answerable;
        replyBot = answerable;

        onSimpleTextMessage(message -> {
            if (message.isMessageFromChat()) return;
            int id = message.authorId();
            this.setId(vk_id);
            String reply = Answerable.respond(message.getText());
            while (reply == null || reply.equals("")) {
                Answerable.restart();
                reply = Answerable.respond(message.getText());
            }
            if (!getUserById(vk_id).isOnline()) {
                new Message()
                        .from(this)
                        .to(id)
                        .text(reply)
                        .send();
                VkUser userById = getUserById(id);
                //if (!getUserById(vk_id).isOnline()){
                secretary.redirect(message.getText(), userById.getFirst_name() + " " + userById.getLast_name(), email);
            }

            //}
        });
        enableTyping(true);
        Answerable = answerable;
        System.out.println("vkBot build with id:" + vk_id);
    }

    public static VkUser getUserById(int id) {
        String request = "https://api.vk.com/method/users.get?access_token=1016899c1016899c1016899cf110786606110161016899c4e07feafbe57356a264ebd63&user_id=" + id + "&fields=online&v=5.21";
        String respond = Connection.getRequestResponse(request);
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
                ExceptionHandler.logException(Thread.currentThread(), e);
                return new VkUser();
            }
        } catch (Exception e) {
            ExceptionHandler.logException(Thread.currentThread(), e);
            return new VkUser();
        }
    }

    private void setAnswerable(answerable answerable) {
        Answerable = answerable;
    }

    @Override
    public void run(String key, int id, answerable answerable, Secretary secretary, bot bot) {
        checkUserOnline(id, secretary);
    }

    @Override
    public void stop() {
        user.setOnPause(true);
        Answerable = secretary.launcher;
        interrupted = true;
    }

    @Override
    public void resume() {
        user.setOnPause(false);

        Answerable = replyBot;
        interrupted = false;
    }

    private void checkUserOnline(int id, Secretary secretary) {
        //if (!thread.isAlive()) return;
        while (!user.isOnPause()) {
            try {

                Thread.sleep(10000);
                System.gc();
                if (getUserById(id).isOnline()) setAnswerable(secretary.launcher);
                else setAnswerable(replyBot.getInstance());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        checkUserOnline(id, secretary);
    }

    @Override
    public answerable getReply() {
        return Answerable;
    }

    synchronized static void send(Message incomeMessage, User user, String reply, String key) {
        Message message1 = new Message();
        message1.from(user);
        message1.setAccessToken(key);
        message1.to(incomeMessage.authorId());
        message1.text(reply)
                .send();
    }

    @Override
    public void setReply(String reply) {
        this.replyBot.setReply(reply);

    }

    public void setup() {
        /*onSimpleTextMessage(message -> {
            author_id=message.authorId();
            System.out.println("received message:  "+message.getText()+"     :from:"+author_id);
            String reply = Answerable.respond(message.getText());
            while (reply == null || reply.equals("")) {
                Answerable.restart();
                reply = Answerable.respond(message.getText());
            }
            System.out.println(reply+": to  "+message.authorId());
            VkUser user = getUserById(message.authorId());
            if (!getUserById(this.id).isOnline()) {

               // secretary.redirect(message.getText(), user.getFirst_name() + " " + user.getLast_name());
                System.out.println("replied " + reply);
                VkBot.send(message,this,reply);
            }

        });*/
    }
}
