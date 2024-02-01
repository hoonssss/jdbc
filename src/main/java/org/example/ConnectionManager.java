package org.example;

import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

public class ConnectionManager {

    public static final String DB_DRIVER = "org.h2.Driver";
    public static final String DB_URL = "jdbc:h2:mem://localhost/-/jdbc-practice;MODE=MySQL;DB_CLOSE_DELAY=-1";
    public static final int MAX_POOL_SIZE = 40;

    private static final DataSource ds;

    /**
     * connection pool은 ds 하나
     * static를 통해 초기화
     * DataSource = hikariDataSource로 설정
     */
    static {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setDriverClassName(DB_DRIVER);
        hikariDataSource.setJdbcUrl(DB_URL);
        hikariDataSource.setUsername("sa");
        hikariDataSource.setPassword("");
        //Connection 최대 개수
        hikariDataSource.setMaximumPoolSize(MAX_POOL_SIZE);
        //Connection 최소 개수
        hikariDataSource.setMinimumIdle(MAX_POOL_SIZE);

        ds = hikariDataSource;
    }


    public static Connection getConnection() {
//        String url = "jdbc:h2:mem://localhost/-/jdbc-practice;MODE=MySQL;DB_CLOSE_DELAY=-1";
//        String id = "sa";
//        String pw = "";
//
//        try {
//            Class.forName("org.h2.Driver");
//            return DriverManager.getConnection(url, id, pw);
//        } catch (Exception e) {
//            return null;
//        }

        /**
         * Datasource에서 Hikari ds에서 Connection을 받아옴
         */
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public static DataSource getDataSource(){
        return ds;
    }
}
