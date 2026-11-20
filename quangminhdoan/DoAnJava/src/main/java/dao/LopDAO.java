package dao;

import db.DBConnection;
import model.Lop;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LopDAO {
    private List<Lop> doc(PreparedStatement ps) throws SQLException {
        List<Lop> ds = new ArrayList<>();
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) ds.add(new Lop(rs.getString(1), rs.getString(2), rs.getString(3)));
        }
        return ds;
    }

    public List<Lop> layTatCa() throws SQLException {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement("SELECT MaLop, TenLop, Khoa FROM Lop ORDER BY MaLop")) {
            return doc(ps);
        }
    }

    public List<Lop> timKiem(String tuKhoa) throws SQLException {
        String sql = "SELECT MaLop, TenLop, Khoa FROM Lop WHERE MaLop LIKE ? OR TenLop LIKE ? OR Khoa LIKE ?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            String k = "%" + tuKhoa + "%";
            ps.setString(1, k); ps.setString(2, k); ps.setString(3, k);
            return doc(ps);
        }
    }

    public void them(Lop l) throws SQLException {
        String sql = "INSERT INTO Lop (MaLop, TenLop, Khoa) VALUES (?, ?, ?)";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, l.maLop); ps.setString(2, l.tenLop); ps.setString(3, l.khoa);
            ps.executeUpdate();
        }
    }

    public void sua(Lop l) throws SQLException {
        String sql = "UPDATE Lop SET TenLop = ?, Khoa = ? WHERE MaLop = ?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, l.tenLop); ps.setString(2, l.khoa); ps.setString(3, l.maLop);
            ps.executeUpdate();
        }
    }

    public void xoa(String maLop) throws SQLException {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement("DELETE FROM Lop WHERE MaLop = ?")) {
            ps.setString(1, maLop);
            ps.executeUpdate();
        }
    }
}
