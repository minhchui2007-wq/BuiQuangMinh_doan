IF DB_ID(N'QuanLySinhVien') IS NULL
    CREATE DATABASE QuanLySinhVien;
GO
USE QuanLySinhVien;
GO
CREATE TABLE Lop (
    MaLop  VARCHAR(10)   NOT NULL PRIMARY KEY,
    TenLop NVARCHAR(100) NOT NULL,
    Khoa   NVARCHAR(100) NULL
);
GO
CREATE TABLE SinhVien (
    MaSV     VARCHAR(10)   NOT NULL PRIMARY KEY,
    HoTen    NVARCHAR(100) NOT NULL,
    NgaySinh DATE          NULL,
    GioiTinh NVARCHAR(5)   NULL,
    DiaChi   NVARCHAR(200) NULL,
    MaLop    VARCHAR(10)   NOT NULL REFERENCES Lop(MaLop)
);
GO
CREATE TABLE Diem (
    Id     INT IDENTITY(1,1) PRIMARY KEY,
    MaSV   VARCHAR(10)   NOT NULL REFERENCES SinhVien(MaSV),
    MonHoc NVARCHAR(100) NOT NULL,
    DiemSo FLOAT         NOT NULL CHECK (DiemSo >= 0 AND DiemSo <= 10)
);
GO
INSERT INTO Lop VALUES ('KHDL17A1', N'Khoa học dữ liệu 17A1', N'Công nghệ thông tin');
INSERT INTO Lop VALUES ('KHDL17A2', N'Khoa học dữ liệu 17A2', N'Công nghệ thông tin');
INSERT INTO SinhVien VALUES ('SV001', N'Nguyễn Văn An', '2005-03-12', N'Nam', N'Hà Nội', 'KHDL17A1');
INSERT INTO SinhVien VALUES ('SV002', N'Trần Thị Bình', '2005-07-25', N'Nữ', N'Nam Định', 'KHDL17A1');
INSERT INTO SinhVien VALUES ('SV003', N'Lê Minh Cường', '2005-11-02', N'Nam', N'Hải Dương', 'KHDL17A2');
INSERT INTO Diem (MaSV, MonHoc, DiemSo) VALUES ('SV001', N'Lập trình Java', 8.5);
INSERT INTO Diem (MaSV, MonHoc, DiemSo) VALUES ('SV002', N'Lập trình Java', 9.0);
GO
