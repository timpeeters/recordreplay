package be.xplore.recordreplay.matcher.stringsimilarity;

import org.junit.Test;

public class NormalizedLevenshteinTest {
    @Test
    public final void testDistance() {
        NormalizedLevenshtein instance = new NormalizedLevenshtein();
        NullEmptyTests.testDistance(instance);
    }

    @Test
    public final void testSimilarity() {
        NormalizedLevenshtein instance = new NormalizedLevenshtein();
        NullEmptyTests.testSimilarity(instance);
    }
}
