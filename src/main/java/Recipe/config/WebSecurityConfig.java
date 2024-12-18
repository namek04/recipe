package Recipe.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    //フォームの値と比較するDBから取得したパスワードは暗号化されているのでフォームの値も暗号化するために利用
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                            "/images/**",
                            "/css/**",
                            "/javascript/**"
                            );
    }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                .authorizeRequests()
                    //「login.html」はログイン不要でアクセス可能に設定
                    .antMatchers("/login").permitAll()
                    //上記以外は直リンク禁止
                    .anyRequest().authenticated()
                .and()
                .formLogin()
                    //ログイン処理のパス
                    .loginProcessingUrl("/login")
                    //ログインページ
                    .loginPage("/login")
                    //ログインエラー時の遷移先 ※パラメーターに「error」を付与
                    .failureUrl("/login?error")
                    //ログイン成功時の遷移先
                    .defaultSuccessUrl("/recipe", true)
                    //ログイン時のキー：名前
                    .usernameParameter("username")
                    //ログイン時のパスワード
                    .passwordParameter("password")
                .and()
                .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutUrl("/logout") //ログアウトのURL
                    .invalidateHttpSession(true)
                    //ログアウト時の遷移先 POSTでアクセス
                    .logoutSuccessUrl("/afterLogout.html");
        }


}