package Vk;

import Bots.ReplyBot;
import Bots.VkBot;
import Secretary.Secretary;
import com.google.gson.Gson;
import interfaces.SocketListener;
import javafx.util.Pair;
import users.VkBotUser;
import util.ExceptionHandler;
import util.SocketThread;

import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class VkBotParser implements SocketListener {
    private Secretary secretary;

    public VkBotParser(Secretary secretary) {
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
        if (message == null) return null;
        if (message.contains("stop")) {
            try {
                int id = Integer.parseInt(message.split("-")[1]);
                secretary.stopUser(id);
            } catch (NumberFormatException e) {
                ExceptionHandler.logException(Thread.currentThread(), e);
            }
        } else if (message.contains("resume")) {
            try {
                int id = Integer.parseInt(message.split("-")[1]);
                secretary.resumeUser(id);
            } catch (NumberFormatException e) {
                ExceptionHandler.logException(Thread.currentThread(), e);
            }
        } else if (message.contains("changeReply-")) {
            try {
                String[] params = message.split("-")[1].split("#");
                secretary.changeReply(params[0], Integer.parseInt(params[1]));
                System.out.println(Arrays.toString(params));
            } catch (Exception e) {
                ExceptionHandler.logException(Thread.currentThread(), e);
            }
        } else if (message.contains("delete")) {
            try {
                int id = Integer.parseInt(message.split("-")[1]);
                secretary.deleteUser(id);
            } catch (NumberFormatException e) {
                ExceptionHandler.logException(Thread.currentThread(), e);
            }
        } else {
            Gson gson = new Gson();
            System.out.println(message);
            VkBotUser user = gson.fromJson(message, VkBotUser.class);
            ReplyBot replyBot = new ReplyBot();
            replyBot.setReply(user.getReply());
            new Thread(() -> {
                VkBot bot = new VkBot(secretary, user, replyBot.getInstance());
                bot.enableTyping(false);
                secretary.addNewUser(new Pair<>(user, bot));
            }).start();

        }
        return null;
    }
}
