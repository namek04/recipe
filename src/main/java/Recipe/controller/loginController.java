package Recipe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import Recipe.entity.Recipe;
import Recipe.form.recipe.GetForm;
import Recipe.service.RecipeService;


@Controller
public class loginController {
	
	private final RecipeService recipeservice;
	
	  @Autowired
	  public loginController(RecipeService recipeservice) {
	      this.recipeservice = recipeservice;
	  }
	  

    //ログイン画面への遷移
    @GetMapping
    String getLogin() {
        return "login";
    }

    //ログイン成功時のメニュー画面への遷移
    @PostMapping
    String postLogin() {
        return "redirect:/recipe";
    }

    /**
     * ログイン成功時に呼び出されるメソッド
     * SecurityContextHolderから認証済みユーザの情報を取得しモデルへ追加する
     * @param model リクエストスコープ上にオブジェクトを載せるためのmap
     * @return helloページのViewName
     */
    @RequestMapping("/recipe")
    public String recipeList(@ModelAttribute GetForm form,Model model) {
    List<Recipe> list = recipeservice.findList(form);
    model.addAttribute("list", list);
    model.addAttribute("getForm", form);
    return "recipe";
}

    //ログアウト成功時の画面へ遷移
    @RequestMapping("/afterLogout")
    String afterLogout() {
        return "afterLogout";
    }

} 