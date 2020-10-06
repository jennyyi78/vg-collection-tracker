package login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dbUtil.dbConnection;

public class LoginModel {
    Connection connection;

    public LoginModel() {
        try {

            this.connection = dbConnection.getConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        if (this.connection == null) {
            System.exit(1);
        }
    }

    public boolean isDatabaseConnected() {
        return this.connection != null;
    }

    public boolean isLogin(String user, String pass) throws Exception {
        PreparedStatement pre = null;
        ResultSet result = null;

        boolean check = false;

        String sql = "Select * from login Where username = ? and password = ?";

        try {
            pre = this.connection.prepareStatement(sql);
            pre.setString(1, user);
            pre.setString(2, pass);

            result = pre.executeQuery();

            if (result.next()) {
                check = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            pre.close();
            result.close();
        }
        return check;
    }

    public boolean alreadyCreated(String user) throws Exception {
        PreparedStatement pre = null;
        ResultSet result = null;

        boolean check = false;

        String sql = "Select * from login Where username = ?";

        try {
            pre = this.connection.prepareStatement(sql);
            pre.setString(1, user);

            result = pre.executeQuery();

            if (result.next()) {
                check = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            pre.close();
            result.close();
        }
        return check;
    }

}
