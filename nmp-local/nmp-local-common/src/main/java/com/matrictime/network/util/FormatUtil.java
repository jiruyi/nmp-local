package com.matrictime.network.util;

public class FormatUtil {

    public static void main(String[] args) {
        long l = 1024*1024*1024*5+11111;
        System.out.println(formatBy1024(l)[0]);
        System.out.println(formatBy1024(l)[1]);

    }

    private static final long KIBI = 1L << 10;
    private static final long MEBI = 1L << 20;
    private static final long GIBI = 1L << 30;
    private static final long TEBI = 1L << 40;
    private static final long PEBI = 1L << 50;
    private static final long EXBI = 1L << 60;


    private static final long KILO = 1_000L;
    private static final long MEGA = 1_000_000L;
    private static final long GIGA = 1_000_000_000L;
    private static final long TERA = 1_000_000_000_000L;
    private static final long PETA = 1_000_000_000_000_000L;
    private static final long EXA = 1_000_000_000_000_000_000L;

    public static String[] formatBy1024(long bytes) {
        if (bytes < KIBI) { // bytes
            return new String[]{String.format("%d", bytes),"B"};
        } else if (bytes < MEBI) { // KiB
            return formatUnits(bytes, KIBI, "K");
        } else if (bytes < GIBI) { // MiB
            return formatUnits(bytes, MEBI, "M");
        } else if (bytes < TEBI) { // GiB
            return formatUnits(bytes, GIBI, "G");
        } else if (bytes < PEBI) { // TiB
            return formatUnits(bytes, TEBI, "T");
        } else if (bytes < EXBI) { // PiB
            return formatUnits(bytes, PEBI, "P");
        } else { // EiB
            return formatUnits(bytes, EXBI, "E");
        }
    }

    public static String[] formatBy1000(long value) {
        if (value < KILO) {
            return new String[]{String.format("%d", value),"B"};
        } else if (value < MEGA) { // K
            return formatUnits(value, KILO, "K");
        } else if (value < GIGA) { // M
            return formatUnits(value, MEGA, "M");
        } else if (value < TERA) { // G
            return formatUnits(value, GIGA, "G");
        } else if (value < PETA) { // T
            return formatUnits(value, TERA, "T");
        } else if (value < EXA) { // P
            return formatUnits(value, PETA, "P");
        } else { // E
            return formatUnits(value, EXA, "E");
        }
    }

    private static String[] formatUnits(long value, long prefix, String unit) {
        String [] res = new String[2];
        res[0] = String.format("%.2f", (double) value / prefix);
        res[1] = unit;
        return res;
    }
}
