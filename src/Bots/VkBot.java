package Bots;

import answerable.answerable;
import com.petersamokhin.bots.sdk.clients.User;
import com.petersamokhin.bots.sdk.longpoll.LongPoll;
import com.petersamokhin.bots.sdk.objects.Message;

public class VkBot extends User  {
    String key;
    private static answerable Answerable;
    public VkBot(String key,answerable answerable)  {
        super(key);
        Answerable=answerable;
        onSimpleTextMessage(message -> {
            new Message()
                    .from(this)
                    .to(message.authorId())
                    .text(Answerable.respond(message.getText()))
                    .send();
        });

    }

    public static void setAnswerable(answerable answerable) {
        Answerable = answerable;
    }
}
