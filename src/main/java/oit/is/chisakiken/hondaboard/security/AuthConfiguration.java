package oit.is.chisakiken.hondaboard.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class AuthConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // $ sshrun htpasswd -nbBC 10 user1 pAssw0rd
        auth.inMemoryAuthentication().withUser("user1")
                .password("$2y$10$rJ9yqGht2W96MdIJICRQQOuUiYrt2eDokKnDuZZof2DPs83PN6QdC").roles("USER");
        auth.inMemoryAuthentication().withUser("user2")
                .password("$2y$10$rJ9yqGht2W96MdIJICRQQOuUiYrt2eDokKnDuZZof2DPs83PN6QdC").roles("USER");
        auth.inMemoryAuthentication().withUser("admin")
                .password("$2y$10$rJ9yqGht2W96MdIJICRQQOuUiYrt2eDokKnDuZZof2DPs83PN6QdC").roles("ADMIN");
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 認証されたユーザがどこにアクセスできるか（認可処理）
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Spring Securityのフォームを利用してログインを行う
        http.formLogin();

        http.csrf().disable();
        http.headers().frameOptions().disable();

        // Spring Securityの機能を利用してログアウト．ログアウト時は http://localhost:8000/ に戻る
        http.logout().logoutSuccessUrl("/");
    }

}
