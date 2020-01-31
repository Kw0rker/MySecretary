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
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:mysql://localhost/vkbotusers?" +
                    "user=root&password=Halflife3?");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Exception occurred on connecting to database\n" + e.getMessage());
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    public void insert(VkBotUser user) throws SQLException {
            Statement statement = conn.createStatement();
            String email = user.getEmail();
            String accessToken = user.getAccessToken();
            int id = user.getId();
            String reply = user.getReply();
        String request = "insert into bot_users  (email, accessToken, id, reply)" + " values (?,?,?,?)";
        PreparedStatement preparedStatement = conn.prepareStatement(request);
        preparedStatement.setString(1, email);
        preparedStatement.setString(2, accessToken);
        preparedStatement.setInt(3, id);
        preparedStatement.setString(4, reply);
        preparedStatement.execute();
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
