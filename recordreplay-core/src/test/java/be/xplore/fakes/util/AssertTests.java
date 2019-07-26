package be.xplore.fakes.util;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AssertTests {
    @Test(expected = IllegalArgumentException.class)
    public void testNotNullWithNull() {
        be.xplore.fakes.util.Assert.notNull(null);
    }

    @Test
    public void testConstructor(){
        Assert assertion = new Assert();
        assertThat(assertion instanceof Assert).isTrue();
    }

    @Test
    public void testNotNullWithObj() {
        Integer integer = 5;
        assertThat(be.xplore.fakes.util.Assert.notNull(integer)).isSameAs(integer);
    }
}
