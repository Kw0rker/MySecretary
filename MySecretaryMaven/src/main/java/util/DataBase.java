package util;

import Bots.VkBot;
import users.VkBotUser;

import java.sql.*;
import java.util.HashSet;

public class DataBase {
    private final String vkUsersTableName = "";
    private Connection conn;

    public DataBase() {
        try {
            String myDriver = "org.gjt.mm.mysql.Driver";
            String myUrl = "jdbc:mysql://localhost/test";
            Class.forName(myDriver);
            conn = DriverManager.getConnection(myUrl, "root", "");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Exception occurred on connecting to database");
        }
    }

    public void insert(VkBotUser user) {
        try {
            Statement statement = conn.createStatement();
            String email = user.getEmail();
            String accessToken = user.getAccessToken();
            int id = user.getId();
            String reply = user.getReply();
            statement.executeUpdate("insert into vkBotUsers (email,accessToken,id,reply) values (`" + email + "`,`" + accessToken + "`,`+" + id + "`,`" + reply + "`)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public VkBot getUser(int id) {
        return null;
    }

    public HashSet<VkBotUser> getAllUsers() {
        HashSet<VkBotUser> users = new HashSet<>();
        try {
            Statement statement = conn.createStatement();
            ResultSet set = statement.executeQuery("SELECT email,accessToken,id,reply FROM vkBotUsers");
            while (set.next()) {
                String email = set.getString("email");
                String accessToken = set.getString("accessToken");
                int id = set.getInt("id");
                String reply = set.getString("reply");
                users.add(new VkBotUser(email, accessToken, id, reply));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}
