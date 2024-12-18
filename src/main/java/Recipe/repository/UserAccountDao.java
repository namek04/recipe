package Recipe.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import Recipe.entity.User;

@Repository
public class UserAccountDao implements IUserAccountDao {

   private final NamedParameterJdbcTemplate jdbcTemplate;
    
   @Autowired
   public UserAccountDao(NamedParameterJdbcTemplate jdbcTemplate) {
       this.jdbcTemplate = jdbcTemplate;
   }
    
   @Override
   public Optional<User> findUser(String userId) {
        
       String sql = "SELECT id, user_id, password, username "
               + "FROM users "
               + "WHERE user_id = :user_id";
       // パラメータ設定用Map
       Map<String, Object> param = new HashMap<>();
       param.put("user_id", userId);
        
       User user = new User();
       // 一件取得
       try {
           Map<String, Object> result = jdbcTemplate.queryForMap(sql, param);
           user.setId((int) result.get("id"));
           user.setUserId((String) result.get("user_id"));
           user.setPassword((String)result.get("password"));
           user.setName((String)result.get("username"));
       }catch(EmptyResultDataAccessException e){
           Optional <User> userOpl = Optional.ofNullable(user);
           return userOpl;
       }
        
       // ラップする
       Optional <User> userOpl = Optional.ofNullable(user);
       return userOpl;
   }
}
