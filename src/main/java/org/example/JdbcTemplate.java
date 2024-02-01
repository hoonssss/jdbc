package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcTemplate {

    public void executeUpdate(String sql, PreparedStatementSetter pss)
        throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql);
            pss.setter(pstmt);

            pstmt.executeUpdate();
        }  finally {
            if(pstmt != null){
                pstmt.close();
            }

            if(con != null){
                con.close();
            }
        }

    }

    /**
     * SQL 입력
     * PrepareStatement 인자를 전달받을려면 Connection 외부에 있어야함(pstat = con.prepareStatement())
     * 그렇게 때문에 PreparedStatementSetter 라는 Interface 전달받음
     **/
    public Object executeUpdate(String sql, PreparedStatementSetter pss, RowMapper rowMapper) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql);
            pss.setter(pstmt);

            rs = pstmt.executeQuery();

            Object obj = null;
            if(rs.next()){
                return rowMapper.map(rs);
            }

            return obj;
        } finally {
            if(pstmt != null){
                pstmt.close();
            }

            if(con != null){
                con.close();
            }
        }
    }


}
