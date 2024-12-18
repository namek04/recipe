package Recipe.repository;

import java.util.Optional;

import Recipe.entity.User;
 
public interface IUserAccountDao {
    // Userを取得
    Optional<User> findUser(String userId);
}