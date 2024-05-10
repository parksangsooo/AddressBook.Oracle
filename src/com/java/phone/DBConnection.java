package com.java.phone;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// 데이터 베이스 설정 및 접속 클래스
public class DBConnection {
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String DB_USER = "himedia";
    private static final String DB_PASS = "himedia";

    public static Connection getConnection() throws SQLException {
        Connection conn = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(URL, DB_USER, DB_PASS);
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC 드라이버를 찾을 수 없어요.");
            e.printStackTrace();

        }
        return conn;
    }
}