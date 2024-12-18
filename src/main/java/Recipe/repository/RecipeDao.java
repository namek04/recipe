package Recipe.repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import Recipe.entity.Recipe;
import Recipe.form.recipe.GetForm;
import Recipe.form.recipe.PostForm;
import Recipe.form.recipe.PutForm;

@Repository
public class RecipeDao implements IRecipeDao {
  
  private final NamedParameterJdbcTemplate jdbcTemplate;
  
  @Autowired
  public RecipeDao(NamedParameterJdbcTemplate jdbcTemplate) {
      this.jdbcTemplate = jdbcTemplate;
  }
  
  @Override
  public List<Recipe> findList(GetForm form) 
  {
	  //グループidでまとめた一覧をすべて表示させるsql文
      StringBuilder sqlBuilder = new StringBuilder();
      sqlBuilder.append("SELECT r.id, r.category, r.title, r.content, TO_CHAR(r.date, 'YYYY/MM/DD') AS date, r.update_datetime, c.name "
              + "FROM recipe AS r INNER JOIN category_code AS c ON r.category = c.cd "
              + "WHERE c.group_cd = '1'");
    
      // パラメータ設定用Map
      Map<String, String> param = new HashMap<>();
      // パラメータが存在した場合、where句にセット
      if(form.getCategory() != null && form.getCategory() != "") {
    	  sqlBuilder.append(" AND c.cd = :cd");
    	  param.put("cd", form.getCategory());
      }
      if(form.getDate() != null && form.getDate() != "") {
    	  sqlBuilder.append(" AND TO_CHAR(r.date, 'YYYY/MM') = :date");
    	  param.put("date", form.getDate());
      }

      String sql = sqlBuilder.toString();

      //タスク一覧をMapのListで取得
      List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, param);
      //return用の空のListを用意
      List<Recipe> list = new ArrayList<Recipe>();

      //データをRecipeにまとめる
      for(Map<String, Object> result : resultList) {
    	  Recipe recipe = new Recipe();
    	  recipe.setId((int)result.get("id"));
    	  recipe.setCategory((String)result.get("category"));
    	  recipe.setTitle((String)result.get("title"));
    	  recipe.setContent((String)result.get("content"));
    	  recipe.setDate((String)result.get("date"));
    	  recipe.setUpdate_datetime((Timestamp)result.get("update_datetime"));
    	  recipe.setName((String)result.get("name"));
    	  list.add(recipe);
      }
      return list;
  }
  //レシピを登録する
  @Override
  public int insert(PostForm form) {
	  // 登録件数を格納
	  int count = 0;
	  String sql = "INSERT INTO recipe(category, title, content, date , update_datetime) "
			  + "VALUES(:category, :title, :content, :date , :update_datetime)";
	  // パラメータ設定用Map
	  Map<String, Object> param = new HashMap<>();
	  param.put("category", form.getCategoryForm());
	  param.put("title", form.getTitleForm());
	  param.put("content", form.getContentForm());
	  param.put("date", form.getDateForm());
	  Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	  param.put("update_datetime", timestamp);
	  count = jdbcTemplate.update(sql, param);
	  return count;
  }
  @Override 
  public Recipe findById(int id) throws  IncorrectResultSizeDataAccessException {
      String sql = "SELECT r.id, r.category, r.title, r.content, TO_CHAR(r.date, 'YYYY/MM/DD') AS date, r.update_datetime, c.name "
              + "FROM recipe AS r INNER JOIN category_code AS c ON r.category = c.cd "
              + "WHERE r.id = :id";
   
      // パラメータ設定用Map
      Map<String, Object> param = new HashMap<>();
      param.put("id", id);
      // 一件取得
      //「queryForMap」はデータ1件を取得するためのメソッド
      Map<String, Object> result = jdbcTemplate.queryForMap(sql, param);
      Recipe recipe = new Recipe();
      recipe.setId((int)result.get("id"));
      recipe.setCategory((String)result.get("category"));
      recipe.setTitle((String)result.get("title"));
      recipe.setContent((String)result.get("content"));
      recipe.setDate((String)result.get("date"));
      recipe.setUpdate_datetime((Timestamp)result.get("update_datetime"));
      recipe.setName((String)result.get("name"));
       
      return recipe;
  }
  //日記を編集する
  @Override
  public int update(PutForm form) {
	  int count = 0;
	  String sql = "UPDATE recipe "
			  + "SET category=:category, title=:title, content=:content, date=:date, update_datetime=:update_datetime "
			  + "WHERE id=:id";
	  // パラメータ設定用Map
	  Map<String, Object> param = new HashMap<>();
	  param.put("id", form.getId());
	  param.put("category", form.getCategoryForm());
	  param.put("title", form.getTitleForm());
	  param.put("content", form.getContentForm());
	  param.put("date", form.getDateForm());
	  Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	  param.put("update_datetime", timestamp);
	  count = jdbcTemplate.update(sql, param);
	  return count;
  }
  //レシピを削除する
  @Override
  public int delete(int id) {
	  int count = 0;
	  String sql = "DELETE FROM recipe "
			  + "WHERE id = :id";
	  // パラメータ設定用Map
	  Map<String, Object> param = new HashMap<>();
	  param.put("id", id);
	  count = jdbcTemplate.update(sql, param);
	  return count;
  }

}
