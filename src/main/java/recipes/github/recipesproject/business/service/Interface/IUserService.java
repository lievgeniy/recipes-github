package recipes.github.recipesproject.business.service.Interface;


import recipes.github.recipesproject.business.model.User;

public interface IUserService {
    User getUser(long id);
    void createUser(User user);
    void deleteUser(long id);
    void updateUser(long id, User newUser);
}
