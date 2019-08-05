package be.xplore.recordreplay;

import be.xplore.recordreplay.testdemo.User;
import org.junit.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTests {

    private static final User.Builder EXAMPLE_BUILDER =
            User.builder().firstName("Fons").lastName("Kwanten").role("Dev").id(1L);

    private final User user1 = EXAMPLE_BUILDER.build();
    private final User user2 = EXAMPLE_BUILDER.build();
    private final User user3 = User.builder().firstName("Kwons").lastName("Kwanten").id(1L).build();
    private final User user4 = User.builder().firstName("Fons").lastName("Fanten").id(1L).build();
    private final User user5 = User.builder().firstName("Fons").lastName("Kwanten").id(2L).build();

    @Test
    public void testEqualsAndHashcode() {
        assertThat(user1.equals(user2))
                .as("Equals method not implemented correctly ")
                .isTrue();
        assertThat(user1.hashCode() == user2.hashCode())
                .as("Hashcode method not implemented correctly")
                .isTrue();
    }

    @Test
    public void notEquals() {
        assertThat(user1.equals(user3))
                .as("FirstName Equals method not implemented correctly ")
                .isFalse();
        assertThat(user1.equals(user4))
                .as("LastName Equals method not implemented correctly ")
                .isFalse();
        assertThat(user1.equals(user5))
                .as("ID Equals method not implemented correctly ")
                .isFalse();
        assertThat(user1.equals(new ArrayList<>()))
                .as("Equals method not implemented correctly ")
                .isFalse();
    }

    @Test
    public void testToString() {
        assertThat(user1.toString())
                .as("toString not implemented correctly")
                .isEqualTo("User[id=1, firstName=Fons, lastName=Kwanten, role=Dev]");
    }
}
