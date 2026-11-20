package ui;

import dao.LopDAO;
import dao.SinhVienDAO;
import model.Lop;
import model.SinhVien;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FormSinhVien extends JFrame {
    private final JTextField txtMa = new JTextField(), txtTen = new JTextField(), txtNgay = new JTextField();
    private final JTextField txtDiaChi = new JTextField(), txtTim = new JTextField(15);
    private final JComboBox<String> cboGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ"});
    private final JComboBox<String> cboLop = new JComboBox<>();
    private final DefaultTableModel model = new DefaultTableModel(
        new String[]{"Mã SV", "Họ tên", "Ngày sinh", "Giới tính", "Địa chỉ", "Lớp"}, 0) {
        @Override public boolean isCellEditable(int r, int c) { return false; }
    };
    private final JTable table = new JTable(model);
    private final SinhVienDAO dao = new SinhVienDAO();

    public FormSinhVien() {
        super("Quản lý sinh viên");
        setSize(800, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel form = new JPanel(new GridLayout(6, 2, 5, 5));
        form.setBorder(BorderFactory.createTitledBorder("Thông tin sinh viên"));
        form.add(new JLabel("Mã sinh viên")); form.add(txtMa);
        form.add(new JLabel("Họ tên")); form.add(txtTen);
        form.add(new JLabel("Ngày sinh (yyyy-MM-dd)")); form.add(txtNgay);
        form.add(new JLabel("Giới tính")); form.add(cboGioiTinh);
        form.add(new JLabel("Địa chỉ")); form.add(txtDiaChi);
        form.add(new JLabel("Lớp")); form.add(cboLop);

        JButton btnThem = new JButton("Thêm"), btnSua = new JButton("Sửa"), btnXoa = new JButton("Xóa");
        JButton btnMoi = new JButton("Làm mới"), btnTim = new JButton("Tìm kiếm");
        JPanel nut = new JPanel(new FlowLayout(FlowLayout.LEFT));
        nut.add(btnThem); nut.add(btnSua); nut.add(btnXoa); nut.add(btnMoi);
        nut.add(new JLabel("   Từ khóa:")); nut.add(txtTim); nut.add(btnTim);

        JPanel bac = new JPanel(new BorderLayout());
        bac.add(form, BorderLayout.CENTER);
        bac.add(nut, BorderLayout.SOUTH);
        add(bac, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        table.getSelectionModel().addListSelectionListener(e -> {
            int r = table.getSelectedRow();
            if (r >= 0) {
                txtMa.setText(String.valueOf(model.getValueAt(r, 0)));
                txtTen.setText(String.valueOf(model.getValueAt(r, 1)));
                txtNgay.setText(String.valueOf(model.getValueAt(r, 2)));
                cboGioiTinh.setSelectedItem(model.getValueAt(r, 3));
                Object dc = model.getValueAt(r, 4);
                txtDiaChi.setText(dc == null ? "" : dc.toString());
                cboLop.setSelectedItem(model.getValueAt(r, 5));
                txtMa.setEditable(false);
            }
        });

        btnThem.addActionListener(e -> them());
        btnSua.addActionListener(e -> sua());
        btnXoa.addActionListener(e -> xoa());
        btnMoi.addActionListener(e -> { lamMoiO(); taiDuLieu(null); });
        btnTim.addActionListener(e -> taiDuLieu(txtTim.getText().trim()));
        taiLop();
        taiDuLieu(null);
    }

    private void taiLop() {
        try {
            cboLop.removeAllItems();
            for (Lop l : new LopDAO().layTatCa()) cboLop.addItem(l.maLop);
        } catch (Exception ex) { loi(ex); }
    }

    private void taiDuLieu(String tuKhoa) {
        try {
            List<SinhVien> ds = (tuKhoa == null || tuKhoa.isEmpty()) ? dao.layTatCa() : dao.timKiem(tuKhoa);
            model.setRowCount(0);
            for (SinhVien s : ds)
                model.addRow(new Object[]{s.maSV, s.hoTen, s.ngaySinh, s.gioiTinh, s.diaChi, s.maLop});
        } catch (Exception ex) { loi(ex); }
    }

    private void lamMoiO() {
        txtMa.setText(""); txtTen.setText(""); txtNgay.setText(""); txtDiaChi.setText(""); txtTim.setText("");
        txtMa.setEditable(true);
        table.clearSelection();
        taiLop();
    }

    private boolean hopLe() {
        if (txtMa.getText().trim().isEmpty() || txtTen.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mã sinh viên và họ tên không được để trống");
            return false;
        }
        if (!txtNgay.getText().trim().matches("\\d{4}-\\d{2}-\\d{2}")) {
            JOptionPane.showMessageDialog(this, "Ngày sinh phải theo dạng yyyy-MM-dd, ví dụ 2005-09-02");
            return false;
        }
        if (cboLop.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Chưa có lớp nào, hãy thêm lớp trước");
            return false;
        }
        return true;
    }

    private SinhVien doiTuong() {
        return new SinhVien(txtMa.getText().trim(), txtTen.getText().trim(), txtNgay.getText().trim(),
            cboGioiTinh.getSelectedItem().toString(), txtDiaChi.getText().trim(),
            cboLop.getSelectedItem().toString());
    }

    private void them() {
        if (!hopLe()) return;
        try {
            dao.them(doiTuong());
            JOptionPane.showMessageDialog(this, "Thêm thành công");
            lamMoiO(); taiDuLieu(null);
        } catch (Exception ex) { loi(ex); }
    }

    private void sua() {
        if (table.getSelectedRow() < 0) { JOptionPane.showMessageDialog(this, "Hãy chọn một dòng để sửa"); return; }
        if (!hopLe()) return;
        try {
            dao.sua(doiTuong());
            JOptionPane.showMessageDialog(this, "Sửa thành công");
            lamMoiO(); taiDuLieu(null);
        } catch (Exception ex) { loi(ex); }
    }

    private void xoa() {
        if (table.getSelectedRow() < 0) { JOptionPane.showMessageDialog(this, "Hãy chọn một dòng để xóa"); return; }
        if (JOptionPane.showConfirmDialog(this, "Xóa sinh viên này?", "Xác nhận", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;
        try {
            dao.xoa(txtMa.getText().trim());
            lamMoiO(); taiDuLieu(null);
        } catch (Exception ex) { loi(ex); }
    }

    private void loi(Exception ex) {
        JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}
