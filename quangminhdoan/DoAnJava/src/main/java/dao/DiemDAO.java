package dao;

import db.DBConnection;
import model.Diem;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DiemDAO {
    private static final String SELECT =
        "SELECT d.Id, d.MaSV, s.HoTen, d.MonHoc, d.DiemSo FROM Diem d JOIN SinhVien s ON d.MaSV = s.MaSV ";

    private List<Diem> doc(PreparedStatement ps) throws SQLException {
        List<Diem> ds = new ArrayList<>();
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next())
                ds.add(new Diem(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDouble(5)));
        }
        return ds;
    }

    public List<Diem> layTatCa() throws SQLException {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SELECT + "ORDER BY d.Id")) {
            return doc(ps);
        }
    }

    public List<Diem> timKiem(String tuKhoa) throws SQLException {
        String sql = SELECT + "WHERE d.MaSV LIKE ? OR s.HoTen LIKE ? OR d.MonHoc LIKE ?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            String k = "%" + tuKhoa + "%";
            ps.setString(1, k); ps.setString(2, k); ps.setString(3, k);
            return doc(ps);
        }
    }

    public void them(Diem d) throws SQLException {
        String sql = "INSERT INTO Diem (MaSV, MonHoc, DiemSo) VALUES (?, ?, ?)";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, d.maSV); ps.setString(2, d.monHoc); ps.setDouble(3, d.diemSo);
            ps.executeUpdate();
        }
    }

    public void sua(Diem d) throws SQLException {
        String sql = "UPDATE Diem SET MaSV = ?, MonHoc = ?, DiemSo = ? WHERE Id = ?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, d.maSV); ps.setString(2, d.monHoc); ps.setDouble(3, d.diemSo); ps.setInt(4, d.id);
            ps.executeUpdate();
        }
    }

    public void xoa(int id) throws SQLException {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement("DELETE FROM Diem WHERE Id = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
