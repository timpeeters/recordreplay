package be.xplore.recordreplay.config;

import org.junit.Test;

import java.util.List;

public class ConfigTest {
    @Test(expected = IllegalArgumentException.class)
    public void malformedTargetThrows() {
        new RecordReplayConfig().target("htyp://cfgvhbjkzeg.com:161894/");
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullMatcherListThrows() {
        new RecordReplayConfig().matchers(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyMatcherListThrows() {
        new RecordReplayConfig().matchers(List.of()).matchers();
    }
}
