package ui;

import dao.DiemDAO;
import dao.SinhVienDAO;
import model.Diem;
import model.SinhVien;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FormDiem extends JFrame {
    private final JTextField txtId = new JTextField(), txtMon = new JTextField(), txtDiem = new JTextField();
    private final JTextField txtTim = new JTextField(15);
    private final JComboBox<String> cboSV = new JComboBox<>();
    private final DefaultTableModel model = new DefaultTableModel(
        new String[]{"Id", "Mã SV", "Họ tên", "Môn học", "Điểm"}, 0) {
        @Override public boolean isCellEditable(int r, int c) { return false; }
    };
    private final JTable table = new JTable(model);
    private final DiemDAO dao = new DiemDAO();

    public FormDiem() {
        super("Quản lý điểm");
        setSize(700, 470);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        txtId.setEditable(false);
        JPanel form = new JPanel(new GridLayout(4, 2, 5, 5));
        form.setBorder(BorderFactory.createTitledBorder("Thông tin điểm"));
        form.add(new JLabel("Id (tự tăng)")); form.add(txtId);
        form.add(new JLabel("Sinh viên")); form.add(cboSV);
        form.add(new JLabel("Môn học")); form.add(txtMon);
        form.add(new JLabel("Điểm (0 đến 10)")); form.add(txtDiem);

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
                txtId.setText(String.valueOf(model.getValueAt(r, 0)));
                cboSV.setSelectedItem(model.getValueAt(r, 1));
                txtMon.setText(String.valueOf(model.getValueAt(r, 3)));
                txtDiem.setText(String.valueOf(model.getValueAt(r, 4)));
            }
        });

        btnThem.addActionListener(e -> them());
        btnSua.addActionListener(e -> sua());
        btnXoa.addActionListener(e -> xoa());
        btnMoi.addActionListener(e -> { lamMoiO(); taiDuLieu(null); });
        btnTim.addActionListener(e -> taiDuLieu(txtTim.getText().trim()));
        taiSinhVien();
        taiDuLieu(null);
    }

    private void taiSinhVien() {
        try {
            cboSV.removeAllItems();
            for (SinhVien s : new SinhVienDAO().layTatCa()) cboSV.addItem(s.maSV);
        } catch (Exception ex) { loi(ex); }
    }

    private void taiDuLieu(String tuKhoa) {
        try {
            List<Diem> ds = (tuKhoa == null || tuKhoa.isEmpty()) ? dao.layTatCa() : dao.timKiem(tuKhoa);
            model.setRowCount(0);
            for (Diem d : ds) model.addRow(new Object[]{d.id, d.maSV, d.hoTen, d.monHoc, d.diemSo});
        } catch (Exception ex) { loi(ex); }
    }

    private void lamMoiO() {
        txtId.setText(""); txtMon.setText(""); txtDiem.setText(""); txtTim.setText("");
        table.clearSelection();
        taiSinhVien();
    }

    private Diem doiTuong() {
        if (cboSV.getSelectedItem() == null || txtMon.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Hãy chọn sinh viên và nhập môn học");
            return null;
        }
        double diem;
        try {
            diem = Double.parseDouble(txtDiem.getText().trim().replace(',', '.'));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Điểm phải là một số");
            return null;
        }
        if (diem < 0 || diem > 10) {
            JOptionPane.showMessageDialog(this, "Điểm phải nằm trong khoảng 0 đến 10");
            return null;
        }
        int id = txtId.getText().isEmpty() ? 0 : Integer.parseInt(txtId.getText());
        return new Diem(id, cboSV.getSelectedItem().toString(), "", txtMon.getText().trim(), diem);
    }

    private void them() {
        Diem d = doiTuong();
        if (d == null) return;
        try {
            dao.them(d);
            JOptionPane.showMessageDialog(this, "Thêm thành công");
            lamMoiO(); taiDuLieu(null);
        } catch (Exception ex) { loi(ex); }
    }

    private void sua() {
        if (txtId.getText().isEmpty()) { JOptionPane.showMessageDialog(this, "Hãy chọn một dòng để sửa"); return; }
        Diem d = doiTuong();
        if (d == null) return;
        try {
            dao.sua(d);
            JOptionPane.showMessageDialog(this, "Sửa thành công");
            lamMoiO(); taiDuLieu(null);
        } catch (Exception ex) { loi(ex); }
    }

    private void xoa() {
        if (txtId.getText().isEmpty()) { JOptionPane.showMessageDialog(this, "Hãy chọn một dòng để xóa"); return; }
        if (JOptionPane.showConfirmDialog(this, "Xóa điểm này?", "Xác nhận", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;
        try {
            dao.xoa(Integer.parseInt(txtId.getText()));
            lamMoiO(); taiDuLieu(null);
        } catch (Exception ex) { loi(ex); }
    }

    private void loi(Exception ex) {
        JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}
