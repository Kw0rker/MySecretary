package Vk;

import Launchers.launcher;

import java.awt.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class VkOauth {
    private final static String app_id ="2685278";
    public static String tmp = "hHbJug59sKJie78wjrH8";
    private final static String  redirect_uri ="https://oauth.vk.com/blank.html";
    private final static String request ="https://oauth.vk.com/authorize?client_id="+ app_id +"&display=popup&redirect_uri="+redirect_uri+"&scope=messages,offline&response_type=token&v=5.103&state=123456";
    private static String[] params;
    public static String[] getParams() {
        return params;
    }

    public static String getAccessToken(launcher Launcher){
        URL url = null;
        HttpURLConnection con=null;
        try {
            url = new URL(request);
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI(request));
            }
            Launcher.printMessage("После подтверждения  скопируйте и вставьте ссылку: ");
            params=Launcher.getMessage().split("access_token=");
            try {
                return params[1].split("&")[0];
            }
            catch (Exception e){Launcher.printMessage("Вы ввели не правильную ссылку или произошла ошибка аутификации");}

        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
}
