package model;

public class Diem {
    public int id;
    public String maSV, hoTen, monHoc;
    public double diemSo;

    public Diem(int id, String maSV, String hoTen, String monHoc, double diemSo) {
        this.id = id;
        this.maSV = maSV;
        this.hoTen = hoTen;
        this.monHoc = monHoc;
        this.diemSo = diemSo;
    }
}
