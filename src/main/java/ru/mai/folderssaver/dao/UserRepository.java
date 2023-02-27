package ru.mai.folderssaver.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.mai.folderssaver.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

  Optional<User> findByEmail(String email);

}
