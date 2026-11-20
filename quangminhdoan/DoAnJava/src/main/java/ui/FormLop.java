package ui;

import dao.LopDAO;
import model.Lop;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FormLop extends JFrame {
    private final JTextField txtMa = new JTextField(), txtTen = new JTextField(), txtKhoa = new JTextField();
    private final JTextField txtTim = new JTextField(15);
    private final DefaultTableModel model = new DefaultTableModel(new String[]{"Mã lớp", "Tên lớp", "Khoa"}, 0) {
        @Override public boolean isCellEditable(int r, int c) { return false; }
    };
    private final JTable table = new JTable(model);
    private final LopDAO dao = new LopDAO();

    public FormLop() {
        super("Quản lý lớp");
        setSize(650, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel form = new JPanel(new GridLayout(3, 2, 5, 5));
        form.setBorder(BorderFactory.createTitledBorder("Thông tin lớp"));
        form.add(new JLabel("Mã lớp")); form.add(txtMa);
        form.add(new JLabel("Tên lớp")); form.add(txtTen);
        form.add(new JLabel("Khoa")); form.add(txtKhoa);

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
                txtMa.setText(model.getValueAt(r, 0).toString());
                txtTen.setText(model.getValueAt(r, 1).toString());
                Object k = model.getValueAt(r, 2);
                txtKhoa.setText(k == null ? "" : k.toString());
                txtMa.setEditable(false);
            }
        });

        btnThem.addActionListener(e -> them());
        btnSua.addActionListener(e -> sua());
        btnXoa.addActionListener(e -> xoa());
        btnMoi.addActionListener(e -> { lamMoiO(); taiDuLieu(null); });
        btnTim.addActionListener(e -> taiDuLieu(txtTim.getText().trim()));
        taiDuLieu(null);
    }

    private void taiDuLieu(String tuKhoa) {
        try {
            List<Lop> ds = (tuKhoa == null || tuKhoa.isEmpty()) ? dao.layTatCa() : dao.timKiem(tuKhoa);
            model.setRowCount(0);
            for (Lop l : ds) model.addRow(new Object[]{l.maLop, l.tenLop, l.khoa});
        } catch (Exception ex) { loi(ex); }
    }

    private void lamMoiO() {
        txtMa.setText(""); txtTen.setText(""); txtKhoa.setText(""); txtTim.setText("");
        txtMa.setEditable(true);
        table.clearSelection();
    }

    private boolean hopLe() {
        if (txtMa.getText().trim().isEmpty() || txtTen.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mã lớp và tên lớp không được để trống");
            return false;
        }
        return true;
    }

    private Lop doiTuong() {
        return new Lop(txtMa.getText().trim(), txtTen.getText().trim(), txtKhoa.getText().trim());
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
        if (JOptionPane.showConfirmDialog(this, "Xóa lớp này?", "Xác nhận", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;
        try {
            dao.xoa(txtMa.getText().trim());
            lamMoiO(); taiDuLieu(null);
        } catch (Exception ex) { loi(ex); }
    }

    private void loi(Exception ex) {
        JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}
