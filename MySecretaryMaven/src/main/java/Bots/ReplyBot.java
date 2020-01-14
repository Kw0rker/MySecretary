package Bots;

import answerable.answerable;

public class ReplyBot implements answerable {
    String reply = "По всей видимости Руслан не в сети \nили же не его нет на месте.\nВаше сообшение перенаправленно на почту\n\n\n\n Вы можете помочь ему улучшить меня❤❤\nhttps://github.com/Kw0rker/MySecretary";
    private static answerable instance;
    @Override
    public String respond(String message) {
        return reply;
    }

    @Override
    public void restart() {

    }

    public void setReply(String reply) {
        instance = this;
        if (!reply.equals("def")) this.reply = reply;
        if (reply.equals("AI")) {
            System.out.println("ai building started");
            instance = new AI();
            System.out.println("ai build finished");
        }
    }

    public answerable getInstance() {
        return instance;
    }
}
