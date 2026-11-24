import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.print.PrinterException;

/** Panel kanan: tabel keranjang, total, points, tombol Checkout/Cetak, dan struk. */
public class KeranjangPanel extends JPanel {

    private final Keranjang keranjang;
    private final DefaultTableModel model;
    private final JLabel lblTotal;
    private final JLabel lblPoints;
    private final JTextArea txtStruk;

    public KeranjangPanel(Keranjang keranjang, Runnable onCheckout, Runnable onCetak) {
        this.keranjang = keranjang;
        setLayout(new BorderLayout(5, 5));

        // Tabel keranjang
        model = new DefaultTableModel(
                new String[]{"ID", "Nama Produk", "Qty", "Harga", "Subtotal"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JScrollPane sp = new JScrollPane(new JTable(model));
        sp.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Keranjang", TitledBorder.LEFT, TitledBorder.TOP));
        sp.setPreferredSize(new Dimension(400, 250));
        add(sp, BorderLayout.NORTH);

        // Total, points, tombol
        lblTotal = new JLabel();
        lblTotal.setFont(lblTotal.getFont().deriveFont(Font.BOLD, 18f));
        lblPoints = new JLabel();
        lblPoints.setFont(lblPoints.getFont().deriveFont(Font.PLAIN, 14f));

        JButton btnCheckout = new JButton("Checkout");
        JButton btnCetak = new JButton("Cetak");
        btnCheckout.addActionListener(e -> onCheckout.run());
        btnCetak.addActionListener(e -> onCetak.run());

        JLabel lblStruk = new JLabel("Struk:");
        lblStruk.setFont(lblStruk.getFont().deriveFont(Font.BOLD));

        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setBorder(BorderFactory.createEmptyBorder(10, 40, 5, 10));
        for (JComponent c : new JComponent[]{lblTotal, lblPoints, btnCheckout, btnCetak, lblStruk})
            c.setAlignmentX(Component.LEFT_ALIGNMENT);
        info.add(lblTotal);
        info.add(Box.createVerticalStrut(8));
        info.add(lblPoints);
        info.add(Box.createVerticalStrut(8));
        info.add(btnCheckout);
        info.add(btnCetak);
        info.add(Box.createVerticalStrut(15));
        info.add(lblStruk);

        // Area struk
        txtStruk = new JTextArea(12, 30);
        txtStruk.setEditable(false);
        txtStruk.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        JPanel tengah = new JPanel(new BorderLayout());
        tengah.add(info, BorderLayout.NORTH);
        tengah.add(new JScrollPane(txtStruk), BorderLayout.CENTER);
        tengah.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 5));
        add(tengah, BorderLayout.CENTER);

        refresh();
    }

    /** Sinkronkan tabel, total, dan points dengan isi objek Keranjang. */
    public void refresh() {
        model.setRowCount(0);
        for (ItemKeranjang item : keranjang.getItems()) {
            model.addRow(new Object[]{
                item.getProduk().getId(),
                item.getProduk().getNama(),
                item.getQty(),
                item.getProduk().getHarga(),
                item.getSubtotal()
            });
        }
        lblTotal.setText("Total: " + FormatRupiah.format(keranjang.getTotal()));
        lblPoints.setText("Points: " + keranjang.getPoints());
    }

    public void setStruk(String teks) {
        txtStruk.setText(teks);
        txtStruk.setCaretPosition(0);
    }

    public boolean strukKosong() { return txtStruk.getText().isEmpty(); }

    public void cetakStruk() throws PrinterException { txtStruk.print(); }
}
