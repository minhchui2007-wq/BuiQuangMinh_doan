package ui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        super("Phần mềm quản lý sinh viên");
        setSize(380, 260);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel p = new JPanel(new GridLayout(3, 1, 10, 10));
        p.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        JButton btnLop = new JButton("Quản lý lớp");
        JButton btnSV = new JButton("Quản lý sinh viên");
        JButton btnDiem = new JButton("Quản lý điểm");
        p.add(btnLop); p.add(btnSV); p.add(btnDiem);
        add(p);

        btnLop.addActionListener(e -> new FormLop().setVisible(true));
        btnSV.addActionListener(e -> new FormSinhVien().setVisible(true));
        btnDiem.addActionListener(e -> new FormDiem().setVisible(true));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
