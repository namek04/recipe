package Recipe.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import Recipe.entity.Recipe;
import Recipe.form.recipe.GetForm;
import Recipe.form.recipe.PostForm;
import Recipe.form.recipe.PutForm;
import Recipe.service.RecipeService;

@Controller
@RequestMapping("/recipe")
public class RecipeController {

  
  private final RecipeService recipeservice;
   
  @Autowired
  public RecipeController(RecipeService recipeservice) {
      this.recipeservice = recipeservice;
  }
  
  /**
   * レシピアプリの一覧画面を表示
   * @param model
   * @return resources/templates/list.html
   */
  @GetMapping
  public String recipeList(
      @ModelAttribute GetForm form,
      Model model
  ) {
      List<Recipe> list = recipeservice.findList(form);
      model.addAttribute("list", list);
      model.addAttribute("getForm", form);
      return "list";
  }
  /**
   * 新規登録へ遷移
   * @param model
   * @return resources/templates/form.html
   */
  @GetMapping("/form")
  public String formPage(
      @ModelAttribute PutForm form,
      Model model
  ) {
      model.addAttribute("putForm", form);
      //新規登録か編集で分岐
      if (form.getUpdateFlag()) {
          model.addAttribute("update", true);
      } else {
          model.addAttribute("update", false);
      }
      return "form";
  }
  
  /**
   * 「一覧へ」選択時、一覧画面へ遷移
   * @param model
   * @return resources/templates/list.html
   */
  //登録せずに一覧画面へ戻る、path={“/insert”, “/form”, "/update"}と指定することで複数のURLとマッピング
  @PostMapping(path={"/insert", "/form", "/update"}, params="back")
  public String backPage(
      Model model
  ) {
      return "redirect:/recipe";
  }
  
  /**
   * 日記を新規登録
   * @param postForm
   * @param model
   * @return
   */
  @PostMapping(path="/insert", params="insert")
  public String insert(
      @Valid @ModelAttribute PostForm form,
      BindingResult result,
      Model model
  ) {
      if(result.hasErrors()) {
          model.addAttribute("error", "パラメータエラーが発生しました。");
          return "form";
      }
      int count = recipeservice.insert(form);
      model.addAttribute("postForm", form);
      return "redirect:/recipe";
  }
  /**
   * 一件タスクデータを取得し、詳細ページ表示
   * @param id
   * @param model
   * @return resources/templates/detail.html
   */
  @GetMapping("/{id}")
  public String showUpdate(
		  //URLに含まれる動的なパラメータを受け取る
		  //GetMappingで「{パラメータ名}」という形
      @PathVariable int id,
      Model model) {
      //Recipeを取得
      Optional<Recipe> recipeOpl = Optional.ofNullable(recipeservice.findById(id));
   
      //NULLかどうかのチェック
      if(recipeOpl.isPresent()) {
          model.addAttribute("recipe", recipeOpl.get());
          return "detail";
      } else {
          model.addAttribute("error", "対象データが存在しません");
          return "detail";
      }
  }
  /**
   * レシピを編集
   * @param putForm
   * @param model
   * @return
   */
  @PostMapping(path="/update", params="update")
  public String update(
      @ModelAttribute PutForm form,
      BindingResult result,
      Model model
  ) {
      if(result.hasErrors()) {
          model.addAttribute("error", "パラメータエラーが発生しました。");
          return "form";
      }
      int count = recipeservice.update(form);
      return "redirect:/recipe";
  }

  
  /**@GetMapping
  public String loginPage(ModelAndView modelAndView) {
    modelAndView.setViewName("loginForm/login");
    modelAndView.addObject("userForm", new Recipe());

    return "login";
  }
  */
  /**
   * レシピを削除
   * @param id
   * @param model
   * @return
   */
  @GetMapping("/delete/{id}")
  public String delete(
	 @PathVariable int id,
	 @ModelAttribute PutForm form,
     Model model) {
      recipeservice.delete(id);
      return "redirect:/recipe";
  }
}
