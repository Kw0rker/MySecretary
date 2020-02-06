package Bots;

import interfaces.answerable;

public class ReplyBot implements answerable {
    String reply = "По всей видимости Руслан не в сети \nили же не его нет на месте.\nВаше сообшение перенаправленно на почту\n\n\n\n Вы можете помочь ему улучшить меня❤❤\nhttps://github.com/Kw0rker/MySecretary";
    private answerable instance = this;
    @Override
    public String respond(String message) {
        return reply;
    }

    @Override
    public void restart() {

    }


    public void setReply(String reply) {
        instance = this;
        if (reply.equals("def")) this.reply = reply;
        else if (reply.equals("AI")) {
            instance = new AI();
        } else this.reply = reply;
        System.out.println(reply);
    }

    public answerable getInstance() {
        return instance;
    }

}
