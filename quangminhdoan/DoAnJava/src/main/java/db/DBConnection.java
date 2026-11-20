package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** Kết nối tới SQL Server. Sửa USER và PASSWORD cho đúng máy của bạn. */
public class DBConnection {
    private static final String URL =
        "jdbc:sqlserver://localhost;instanceName=SQLEXPRESS01;databaseName=QuanLySinhVien;encrypt=true;trustServerCertificate=true";
    private static final String USER = "sa";
    private static final String PASSWORD = "Netbeans@2026";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
