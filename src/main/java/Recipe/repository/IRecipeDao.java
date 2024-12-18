package Recipe.repository;

import java.util.List;

import Recipe.entity.Recipe;
import Recipe.form.recipe.GetForm;
import Recipe.form.recipe.PostForm;
import Recipe.form.recipe.PutForm;

public interface IRecipeDao {
  // 登録されているレシピを取得
  List<Recipe> findList(GetForm form);
  //レシピを登録する
  int insert(PostForm form);
  //idを指定してレシピを1件取得
  Recipe findById(int id);
  //レシピを更新する
  int update(PutForm form);
  //レシピを削除する
  int delete(int id);
}
