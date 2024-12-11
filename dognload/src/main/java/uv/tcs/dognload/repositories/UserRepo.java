package uv.tcs.dognload.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uv.tcs.dognload.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
}
