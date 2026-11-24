import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/** Utilitas format angka ke rupiah, contoh: Rp14.000,00 */
public class FormatRupiah {
    private static final DecimalFormat DF;

    static {
        DecimalFormatSymbols s = new DecimalFormatSymbols(new Locale("id", "ID"));
        s.setGroupingSeparator('.');
        s.setDecimalSeparator(',');
        DF = new DecimalFormat("#,##0.00", s);
    }

    private FormatRupiah() {}

    public static String format(int nilai) {
        return "Rp" + DF.format(nilai);
    }
}
