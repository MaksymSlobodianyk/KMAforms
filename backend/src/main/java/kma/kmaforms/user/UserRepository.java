package kma.kmaforms.user;

import kma.kmaforms.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    public List<User> getAllByEmailIsNot(String email);
}
