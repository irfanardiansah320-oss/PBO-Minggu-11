import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Logika bisnis keranjang belanja (tanpa kode GUI). */
public class Keranjang {
    private static final int RUPIAH_PER_POINT = 1000;
    private final List<ItemKeranjang> items = new ArrayList<>();

    public void tambah(Produk produk, int qty) {
        for (ItemKeranjang item : items) {
            if (item.getProduk().getId().equals(produk.getId())) {
                item.tambahQty(qty);   // produk sama -> gabung qty
                return;
            }
        }
        items.add(new ItemKeranjang(produk, qty));
    }

    public List<ItemKeranjang> getItems() { return Collections.unmodifiableList(items); }

    public int getTotal() {
        int total = 0;
        for (ItemKeranjang item : items) total += item.getSubtotal();
        return total;
    }

    public int getPoints()    { return getTotal() / RUPIAH_PER_POINT; }
    public boolean isEmpty()  { return items.isEmpty(); }
    public void kosongkan()   { items.clear(); }
}
