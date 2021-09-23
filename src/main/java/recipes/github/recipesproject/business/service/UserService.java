package recipes.github.recipesproject.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import recipes.github.recipesproject.business.model.User;
import recipes.github.recipesproject.business.service.Interface.IUserService;
import recipes.github.recipesproject.persistence.repository.Interface.IUserRepository;
import recipes.github.recipesproject.presentation.error.BadParametersException;
import recipes.github.recipesproject.presentation.error.ItemNotFoundException;

@Service
public class UserService implements IUserService {

    private final IUserRepository userRepository;

    @Autowired
    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public User getUser(long id) {
        return this.userRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("User not found with id: " + id));
    }

    @Override
    public void createUser(User user) {
        if (this.userRepository.findUserByEmail(user.getEmail()).isPresent()) {
            throw new BadParametersException("User already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);
        user.setRoles("ROLE_USER");
        this.userRepository.save(user);
    }

    @Override
    public void deleteUser(long id) {
        this.userRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("User not found with id: " + id));
        this.userRepository.deleteById(id);
    }

    //need to check if new password or old password in order to know if the password needs reencryption
    @Override
    public void updateUser(long id, User newUser) {
        User oldUser = this.userRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("User not found with id: " + id));
        oldUser.setEmail(newUser.getEmail());
        oldUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        this.userRepository.save(oldUser);
    }
}
