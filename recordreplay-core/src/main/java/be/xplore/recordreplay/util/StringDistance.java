package be.xplore.recordreplay.util;

import info.debatty.java.stringsimilarity.NormalizedLevenshtein;

public final class StringDistance {

    private static final NormalizedLevenshtein LEVENSHTEIN = new NormalizedLevenshtein();

    public static double calculate(String str1, String str2) {
        return LEVENSHTEIN.distance(str1, str2);
    }
}
