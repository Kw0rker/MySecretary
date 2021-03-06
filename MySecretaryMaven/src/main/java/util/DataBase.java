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
            conn = DriverManager.getConnection("jdbc:mysql://localhost/mysecretary?" +
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
        String request = "INSERT INTO bot_users  (email, accessToken, id, reply,onPause)" + " values (?,?,?,?,?)";
        PreparedStatement preparedStatement = conn.prepareStatement(request);
        preparedStatement.setString(1, email);
        preparedStatement.setString(2, accessToken);
        preparedStatement.setInt(3, id);
        preparedStatement.setString(4, reply);
        preparedStatement.setInt(5, 0);
        preparedStatement.execute();
    }

    public VkBot getUser(int id) {
        return null;
    }

    public HashSet<VkBotUser> getAllUsers() {
        HashSet<VkBotUser> users = new HashSet<>();
        try {
            Statement statement = conn.createStatement();
            ResultSet set = statement.executeQuery("SELECT email,accessToken,id,reply,onPause FROM bot_users");
            while (set.next()) {
                String email = set.getString("email");
                String accessToken = set.getString("accessToken");
                int id = set.getInt("id");
                String reply = set.getString("reply");
                boolean isOnPause = set.getInt("onPause") == 1;
                users.add(new VkBotUser(email, accessToken, id, reply, isOnPause));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void changeReply(String reply, int id) throws SQLException {
        String query = "UPDATE bot_users SET reply = ? WHERE bot_users.id = ?";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, reply);
            preparedStmt.setInt(2, id);
            preparedStmt.execute();
    }

    public void deleteUser(int id) throws SQLException {
        String query = "DELETE  from bot_users WHERE bot_users.id=?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
    }

    public void pauseUser(int id) throws SQLException {
        String query = "UPDATE   bot_users SET onPause=1 WHERE bot_users.id=?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
    }

    public void resumeUser(int id) throws SQLException {
        String query = "UPDATE   bot_users SET onPause=0 WHERE bot_users.id=?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
    }
}
