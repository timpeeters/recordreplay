package be.xplore.demorest.repository;

import be.xplore.demorest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
}
