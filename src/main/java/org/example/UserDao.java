package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

//    public void create(User user) throws SQLException {
//        Connection con = null;
//        PreparedStatement pstmt = null;
//
//        try {
//            con = ConnectionManager.getConnection();
//            String sql = "INSERT INTO USERS VALUES (?,?,?,?)";
//            pstmt = con.prepareStatement(sql);
//
//            pstmt.setString(1, user.getUserId());
//            pstmt.setString(2, user.getPassword());
//            pstmt.setString(3, user.getName());
//            pstmt.setString(4, user.getName());
//
//            pstmt.executeUpdate();
//        } finally {
//            if (pstmt != null) {
//                pstmt.close();
//            }
//
//            if (con != null) {
//                con.close();
//            }
//        }
//    }

    public void create(User user) throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();

        String sql = "INSERT INTO USERS VALUES (?,?,?,?)";
        jdbcTemplate.executeUpdate(sql, pstmt -> {
            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getEmail()
            );
        });
    }

//    public User findByUserId(String userId) throws SQLException {
//        Connection con = null;
//        PreparedStatement pspmt = null;
//        ResultSet rs = null;
//
//        try {
//            con = ConnectionManager.getConnection();
//            String sql = "SELECT userId, password, name, email FROM USERS WHERE userId = ?";
//            pspmt = con.prepareStatement(sql);
//            pspmt.setString(1, userId);
//
//            rs = pspmt.executeQuery();
//
//            User user = null;
//            if (rs.next()) {
//                user = new User(
//                    rs.getString("userId"),
//                    rs.getString("password"),
//                    rs.getString("name"),
//                    rs.getString("email")
//                );
//            }
//            return user;
//        } finally { //거꾸로 올라감
//            if (rs != null) {
//                rs.close();
//            }
//
//            if (pspmt != null) {
//                pspmt.close();
//            }
//
//            if (con != null) {
//                con.close();
//            }
//        }
//    }

    public User findByUserId(String userId) throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "SELECT userId, password, name, email FROM USERS WHERE userId = ?";
        return (User) jdbcTemplate.executeUpdate(sql,
            pstmt -> pstmt.setString(1, userId),
            resultSet -> new User(
                resultSet.getString("userId"),
                resultSet.getString("password"),
                resultSet.getString("name"),
                resultSet.getString("email")
            ));
    }
}
