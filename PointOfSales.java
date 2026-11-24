import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.print.PrinterException;
import java.util.Arrays;
import java.util.List;

/** Frame utama + main(). Hanya merangkai panel dan menghubungkan event. */
public class PointOfSales extends JFrame {

    private final Keranjang keranjang = new Keranjang();
    private final KeranjangPanel keranjangPanel;

    public PointOfSales() {
        super("POIN Off-Sales - Java Swing");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setJMenuBar(buatMenu());

        List<Produk> daftarProduk = Arrays.asList(
            new Produk("P001", "Air Mineral 600ml", 3000),
            new Produk("P002", "Kopi Sachet", 5000),
            new Produk("P003", "Roti isi", 8000),
            new Produk("P004", "Snack Keripik", 6000),
            new Produk("P005", "Minuman Botol", 12000)
        );

        ProdukPanel produkPanel = new ProdukPanel(daftarProduk, this::tambahKeKeranjang);
        keranjangPanel = new KeranjangPanel(keranjang, this::checkout, this::cetak);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, produkPanel, keranjangPanel);
        split.setResizeWeight(0.45);
        add(split, BorderLayout.CENTER);
    }

    private JMenuBar buatMenu() {
        JMenu file = new JMenu("File");
        JMenuItem keluar = new JMenuItem("Exit");
        keluar.addActionListener(e -> System.exit(0));
        file.add(keluar);

        JMenu help = new JMenu("Help");
        JMenuItem about = new JMenuItem("About");
        about.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "POIN Off-Sales\nAplikasi POS sederhana (Java Swing)"));
        help.add(about);

        JMenuBar bar = new JMenuBar();
        bar.add(file);
        bar.add(help);
        return bar;
    }

    private void tambahKeKeranjang(Produk produk, int qty) {
        keranjang.tambah(produk, qty);
        keranjangPanel.refresh();
    }

    private void checkout() {
        if (keranjang.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Keranjang masih kosong!");
            return;
        }
        keranjangPanel.setStruk(StrukBuilder.buat(keranjang));
        keranjang.kosongkan();
        keranjangPanel.refresh();
    }

    private void cetak() {
        if (keranjangPanel.strukKosong()) {
            JOptionPane.showMessageDialog(this, "Belum ada struk. Lakukan Checkout dulu!");
            return;
        }
        try {
            keranjangPanel.cetakStruk();
        } catch (PrinterException ex) {
            JOptionPane.showMessageDialog(this, "Gagal mencetak: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PointOfSales().setVisible(true));
    }
}
