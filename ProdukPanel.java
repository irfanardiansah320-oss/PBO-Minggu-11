import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/** Panel kiri: tabel produk, label pilihan, Qty, dan tombol Add to Cart. */
public class ProdukPanel extends JPanel {

    /** Callback ke luar saat tombol Add to Cart ditekan dengan data valid. */
    public interface AddToCartListener {
        void onAdd(Produk produk, int qty);
    }

    private final List<Produk> daftarProduk;
    private final JTable tabel;
    private final JLabel lblDipilih;
    private final JTextField txtQty;

    public ProdukPanel(List<Produk> daftarProduk, AddToCartListener listener) {
        this.daftarProduk = daftarProduk;
        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Produk", TitledBorder.LEFT, TitledBorder.TOP));

        // Tabel produk
        DefaultTableModel model = new DefaultTableModel(
                new String[]{"ID", "Nama Produk", "Harga (Rp)"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        for (Produk p : daftarProduk)
            model.addRow(new Object[]{p.getId(), p.getNama(), p.getHarga()});

        tabel = new JTable(model);
        tabel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabel.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) updateLabelDipilih();
        });
        add(new JScrollPane(tabel), BorderLayout.CENTER);

        // Bagian bawah
        lblDipilih = new JLabel("Pilih produk dari tabel di kiri");
        lblDipilih.setFont(lblDipilih.getFont().deriveFont(Font.BOLD));
        lblDipilih.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 0));

        txtQty = new JTextField("1", 4);
        JButton btnAdd = new JButton("Add to Cart");
        btnAdd.addActionListener(e -> prosesAdd(listener));

        JPanel barisQty = new JPanel(new FlowLayout(FlowLayout.LEFT));
        barisQty.add(new JLabel("Qty:"));
        barisQty.add(txtQty);
        barisQty.add(btnAdd);

        JPanel bawah = new JPanel(new GridLayout(2, 1));
        bawah.add(lblDipilih);
        bawah.add(barisQty);
        add(bawah, BorderLayout.SOUTH);
    }

    private Produk getProdukTerpilih() {
        int r = tabel.getSelectedRow();
        return (r < 0) ? null : daftarProduk.get(r);
    }

    private void updateLabelDipilih() {
        Produk p = getProdukTerpilih();
        if (p == null) return;
        lblDipilih.setText("Dipilih: " + p.getId() + " - " + p.getNama()
                + " (" + FormatRupiah.format(p.getHarga()) + ")");
    }

    private void prosesAdd(AddToCartListener listener) {
        Produk p = getProdukTerpilih();
        if (p == null) {
            JOptionPane.showMessageDialog(this, "Pilih produk terlebih dahulu!");
            return;
        }
        int qty;
        try {
            qty = Integer.parseInt(txtQty.getText().trim());
            if (qty <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Qty harus berupa angka lebih dari 0!");
            return;
        }
        listener.onAdd(p, qty);
    }
}
