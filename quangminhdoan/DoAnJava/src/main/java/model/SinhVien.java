package model;

public class SinhVien {
    public String maSV, hoTen, ngaySinh, gioiTinh, diaChi, maLop;

    public SinhVien(String maSV, String hoTen, String ngaySinh, String gioiTinh, String diaChi, String maLop) {
        this.maSV = maSV;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.diaChi = diaChi;
        this.maLop = maLop;
    }

    @Override
    public String toString() { return maSV; }
}
