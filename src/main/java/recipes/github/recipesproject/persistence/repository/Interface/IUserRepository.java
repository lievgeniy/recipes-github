package recipes.github.recipesproject.persistence.repository.Interface;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import recipes.github.recipesproject.business.model.User;

import java.util.Optional;

@Repository
public interface IUserRepository extends CrudRepository<User, Long> {
    Optional<User> findUserByEmail(String email);
}
