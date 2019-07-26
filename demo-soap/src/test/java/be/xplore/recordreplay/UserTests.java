package be.xplore.recordreplay;

import org.junit.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTests {

    private final User user1 = User.builder().firstName("Fons").lastName("Kwanten").role("Dev").id(1L).build();
    private final User user2 = User.builder().firstName("Fons").lastName("Kwanten").role("Dev").id(1L).build();
    private final User user3 = User.builder().firstName("Kwons").lastName("Fanten").role("Dev").id(1L).build();

    @Test
    public void testEqualsAndHashcode(){
        assertThat(user1.equals(user2))
                .as("Equals method not implemented correctly ")
                .isTrue();
        assertThat(user1.equals(user3))
                .as("Equals method not implemented correctly ")
                .isFalse();
        assertThat(user1.equals(new ArrayList<>()))
                .as("Equals method not implemented correctly ")
                .isFalse();
        assertThat(user1.hashCode() == user2.hashCode())
                .as("Hashcode method not implemented correctly")
                .isTrue();
    }

    @Test
    public void testToString() {
        assertThat(user1.toString())
                .as("toString not implemented correctly")
                .isEqualTo("User[id=1, firstName=Fons, lastName=Kwanten, role=Dev]");
    }
}
