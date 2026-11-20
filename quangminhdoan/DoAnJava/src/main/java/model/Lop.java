package model;

public class Lop {
    public String maLop, tenLop, khoa;

    public Lop(String maLop, String tenLop, String khoa) {
        this.maLop = maLop;
        this.tenLop = tenLop;
        this.khoa = khoa;
    }

    @Override
    public String toString() { return maLop; }
}
