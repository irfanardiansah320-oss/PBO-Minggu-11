/** Membuat teks struk dari isi keranjang. */
public class StrukBuilder {

    private StrukBuilder() {}

    public static String buat(Keranjang keranjang) {
        StringBuilder sb = new StringBuilder();
        sb.append("===== POIN OFF-SALES =====\n");
        sb.append("Toko: Demo Toko\n");
        sb.append("================================\n");
        sb.append("ID   Nama        Qty  Subtotal\n");
        sb.append("--------------------------------\n");
        for (ItemKeranjang item : keranjang.getItems()) {
            sb.append(item.getProduk().getId()).append("  ")
              .append(item.getProduk().getNama()).append("   ")
              .append(item.getQty()).append("  ")
              .append(FormatRupiah.format(item.getSubtotal())).append("\n");
        }
        sb.append("--------------------------------\n");
        sb.append("TOTAL:        ").append(FormatRupiah.format(keranjang.getTotal())).append("\n");
        sb.append("POINTS DIDAPAT: ").append(keranjang.getPoints()).append(" (1 point per Rp 1000)\n");
        sb.append("================================\n");
        sb.append("Terima kasih! Silakan kunjungi kembali.\n");
        return sb.toString();
    }
}
