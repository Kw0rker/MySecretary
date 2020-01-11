package Bots;

import answerable.answerable;

public class ReplyBot implements answerable {
    String reply = "По всей видимости Руслан не в сети \nили же не его нет на месте.\nВаше сообшение перенаправленно на почту\n\n\n\n Вы можете помочь ему улучшить меня❤❤\nhttps://github.com/Kw0rker/MySecretary";
    @Override
    public String respond(String message) {
        return reply;
    }

    public void setReply(String reply) {
        if (!reply.equals("def")) this.reply = reply;
    }
}
