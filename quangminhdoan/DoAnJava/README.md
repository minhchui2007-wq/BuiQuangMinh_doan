# Phần mềm quản lý sinh viên (Java Swing + JDBC + SQL Server)

Đồ án môn Lập trình Java. Phần mềm kết nối SQL Server bằng JDBC, có 3 form, mỗi form đều có chức năng thêm, sửa, xóa, tìm kiếm.

## Chức năng

- Form Quản lý lớp: thêm, sửa, xóa, tìm theo mã lớp, tên lớp, khoa.
- Form Quản lý sinh viên: thêm, sửa, xóa, tìm theo mã, họ tên, lớp, địa chỉ.
- Form Quản lý điểm: thêm, sửa, xóa, tìm theo mã sinh viên, họ tên, môn học. Điểm phải từ 0 đến 10.

## Cấu trúc

- sql/QuanLySinhVien.sql: tạo cơ sở dữ liệu, 3 bảng (Lop, SinhVien, Diem) và dữ liệu mẫu.
- src/main/java/db: lớp kết nối SQL Server.
- src/main/java/model: các lớp dữ liệu.
- src/main/java/dao: các lớp truy vấn, dùng INSERT, UPDATE, DELETE, SELECT qua PreparedStatement.
- src/main/java/ui: MainFrame và 3 form.

## Cách chạy

1. Mở SQL Server Management Studio, mở file sql/QuanLySinhVien.sql và bấm Execute.
2. Mở SQL Server Configuration Manager, bật TCP/IP cho SQL Server, cổng 1433, rồi khởi động lại dịch vụ SQL Server.
3. Mở file src/main/java/db/DBConnection.java, sửa USER và PASSWORD cho đúng tài khoản SQL Server của bạn.
4. Mở thư mục dự án bằng VS Code (cần Extension Pack for Java), chờ Maven tải thư viện mssql-jdbc.
5. Mở src/main/java/ui/MainFrame.java và bấm Run.

## Công nghệ

Java 8 trở lên, Swing, Maven, Microsoft JDBC Driver for SQL Server, SQL Server.
