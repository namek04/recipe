package Recipe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Recipe.entity.Recipe;
import Recipe.form.recipe.GetForm;
import Recipe.form.recipe.PostForm;
import Recipe.form.recipe.PutForm;
import Recipe.repository.IRecipeDao;

@Service
@Transactional
public class RecipeService {

  private final IRecipeDao dao;
  
  @Autowired
  public RecipeService(IRecipeDao dao) {
      this.dao = dao;
  }
  
  public List<Recipe> findList(GetForm form) {
    return dao.findList(form);
  }
  public int insert(PostForm form) {
	    return dao.insert(form);
  }
  /*エラー処理
　	Daoクラスでthrowしたエラーをtry〜catchで受け取る。
　	1件もデータを取得できない場合はnullを返す。
   */
  public Recipe findById(int id) {
  	try {
  		return dao.findById(id);
  	} catch(IncorrectResultSizeDataAccessException e) {
  		return null;
  	}
  }
  public int update(PutForm form) {
	    return dao.update(form);
  }
  public int delete(int id) {
	    return dao.delete(id);
  }
}
