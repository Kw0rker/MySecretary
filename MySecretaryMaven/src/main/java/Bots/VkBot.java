package Bots;

import Secretary.Secretary;
import answerable.answerable;
import com.petersamokhin.bots.sdk.clients.User;
import com.petersamokhin.bots.sdk.objects.Message;

public class VkBot extends User  {
    private Secretary secretary;
    private static answerable Answerable;

    public VkBot(String key, answerable answerable, Secretary secretary) {
        super(key);
        this.secretary = secretary;
        Answerable=answerable;
        onSimpleTextMessage(message -> {

            new Message()
                    .from(this)
                    .to(message.authorId())
                    .text(Answerable.respond(message.getText()))
                    .send();
            secretary.redirect(message.getText());
        });

    }

    public static void setAnswerable(answerable answerable) {
        Answerable = answerable;
    }
}
