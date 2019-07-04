package be.xplore.demosoap;

import java.util.Objects;
import java.util.StringJoiner;

public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String role;

    private User() {
    }

    private User(Builder builder) {
        this.id = builder.id;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.role = builder.role;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(final Object otherObject) {
        if (!(otherObject instanceof User)) {
            return false;
        }

        User otherUser = (User) otherObject;

        return Objects.equals(getId(), otherUser.getId()) &&
                Objects.equals(getFirstName(), otherUser.getFirstName()) &&
                Objects.equals(getLastName(), otherUser.getLastName());
    }


    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", "User[", "]")
                .add("id=" + getId())
                .add("firstName=" + getFirstName())
                .add("lastName=" + getLastName())
                .add("role=" + getRole())
                .toString();
    }

    public static class Builder {
        private Long id;
        private String firstName;
        private String lastName;
        private String role;

        public Builder id(Long id) {
            this.id = id;

            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;

            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;

            return this;
        }

        public Builder role(String role) {
            this.role = role;

            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
