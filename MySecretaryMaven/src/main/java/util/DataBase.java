package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase {
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

    public void insert() {
    }
}
