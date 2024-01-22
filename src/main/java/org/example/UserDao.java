package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    /**
     * ConnectionManager 와 동일하게
     **/
    private Connection getConnection() {
        String url = "jdbc:h2:mem://localhost/-/jdbc-practice;MODE=MySQL;DB_CLOSE_DELAY=-1";
        String id = "sa";
        String pw = "";

        try {
            Class.forName("org.h2.Driver");
            return DriverManager.getConnection(url, id, pw);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * SQL 입력
     **/
    public void create(User user) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();
            String sql = "INSERT INTO USERS VALUES (?,?,?,?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, user.getUserId()); //userId
            pstmt.setString(2, user.getPassword()); //password
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getEmail());

            pstmt.executeUpdate();
        } finally { //자원 해제
            if (pstmt != null) {
                pstmt.close();
            }

            if (con != null) {
                con.close();
            }
        }
    }

    public User findByUserId(String userId) throws SQLException {
        Connection con = null;
        PreparedStatement pspmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            String sql = "SELECT userId, password, name, email FROM USERS WHERE userId = ?";
            pspmt = con.prepareStatement(sql);
            pspmt.setString(1, userId);

            rs = pspmt.executeQuery();

            User user = null;
            if (rs.next()) {
                user = new User(
                    rs.getString("userId"),
                    rs.getString("password"),
                    rs.getString("name"),
                    rs.getString("email")
                );
            }
            return user;
        }finally { //거꾸로 올라감
            if(rs != null){
                rs.close();
            }
            if(pspmt != null){
                pspmt.close();
            }
            if(con != null){
                con.close();
            }
        }
    }
}
