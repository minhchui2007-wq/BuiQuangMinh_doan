package dao;

import db.DBConnection;
import model.SinhVien;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SinhVienDAO {
    private static final String SELECT =
        "SELECT MaSV, HoTen, CONVERT(VARCHAR(10), NgaySinh, 23), GioiTinh, DiaChi, MaLop FROM SinhVien ";

    private List<SinhVien> doc(PreparedStatement ps) throws SQLException {
        List<SinhVien> ds = new ArrayList<>();
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next())
                ds.add(new SinhVien(rs.getString(1), rs.getString(2), rs.getString(3),
                                    rs.getString(4), rs.getString(5), rs.getString(6)));
        }
        return ds;
    }

    public List<SinhVien> layTatCa() throws SQLException {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SELECT + "ORDER BY MaSV")) {
            return doc(ps);
        }
    }

    public List<SinhVien> timKiem(String tuKhoa) throws SQLException {
        String sql = SELECT + "WHERE MaSV LIKE ? OR HoTen LIKE ? OR MaLop LIKE ? OR DiaChi LIKE ?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            String k = "%" + tuKhoa + "%";
            for (int i = 1; i <= 4; i++) ps.setString(i, k);
            return doc(ps);
        }
    }

    public void them(SinhVien s) throws SQLException {
        String sql = "INSERT INTO SinhVien (MaSV, HoTen, NgaySinh, GioiTinh, DiaChi, MaLop) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, s.maSV);
            ps.setString(2, s.hoTen);
            ps.setDate(3, Date.valueOf(s.ngaySinh));
            ps.setString(4, s.gioiTinh);
            ps.setString(5, s.diaChi);
            ps.setString(6, s.maLop);
            ps.executeUpdate();
        }
    }

    public void sua(SinhVien s) throws SQLException {
        String sql = "UPDATE SinhVien SET HoTen = ?, NgaySinh = ?, GioiTinh = ?, DiaChi = ?, MaLop = ? WHERE MaSV = ?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, s.hoTen);
            ps.setDate(2, Date.valueOf(s.ngaySinh));
            ps.setString(3, s.gioiTinh);
            ps.setString(4, s.diaChi);
            ps.setString(5, s.maLop);
            ps.setString(6, s.maSV);
            ps.executeUpdate();
        }
    }

    public void xoa(String maSV) throws SQLException {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement("DELETE FROM SinhVien WHERE MaSV = ?")) {
            ps.setString(1, maSV);
            ps.executeUpdate();
        }
    }
}
