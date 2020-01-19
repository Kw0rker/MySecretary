package Vk;

import Bots.ReplyBot;
import Bots.VkBot;
import Secretary.Secretary;
import com.google.gson.Gson;
import interfaces.SocketListener;
import javafx.util.Pair;
import users.VkBotUser;
import util.SocketThread;

import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

class VkBotParser implements SocketListener {
    private Secretary secretary;

    VkBotParser(Secretary secretary) {
        this.secretary = secretary;
        SocketThread socketThread = new SocketThread(this, 24444);
    }

    @Override
    public SocketThread socketThreadCreated(Thread thread) {
        return null;
    }

    @Override
    public ServerSocket socketCreated(ServerSocket socket) {
        return null;
    }

    @Override
    public Socket socketAccepted(Socket socket) {
        return null;
    }

    @Override
    public InputStreamReader inputStreamCreated(InputStreamReader inputStreamReader) {
        return null;
    }

    @Override
    public String messageGet(String message) {
        Gson gson = new Gson();
        VkBotUser user = gson.fromJson(message, VkBotUser.class);
        ReplyBot replyBot = new ReplyBot();
        replyBot.setReply(user.getReply());
        VkBot bot = new VkBot(user.getAccessToken(), user.getId(), replyBot, secretary);
        secretary.addNewUser(new Pair<>(user, bot));
        return null;
    }
}
