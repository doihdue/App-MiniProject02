package com.example.miniproject02.util;

import java.text.NumberFormat;
import java.util.Locale;

public final class FormatUtils {
    private FormatUtils() {
    }

    public static String toVnd(int amount) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
        return formatter.format(amount) + " VND";
    }
}
