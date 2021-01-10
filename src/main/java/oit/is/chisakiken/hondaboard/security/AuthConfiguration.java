package oit.is.chisakiken.hondaboard.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class AuthConfiguration extends WebSecurityConfigurerAdapter {
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/favicon.ico", "/img/**");
    }

    /**
     * 認証されたユーザがどこにアクセスできるか（認可処理）
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Spring Securityのフォームを利用してログインを行う
        http.formLogin().loginProcessingUrl("/login").loginPage("/").defaultSuccessUrl("/userpage", true);
        // 非ログインユーザ許可
        http.authorizeRequests().antMatchers("/", "/register", "/event/all", "/chatpage/sse").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/chatpage").permitAll();

        http.authorizeRequests().anyRequest().authenticated();

        http.csrf().disable();
        http.headers().frameOptions().disable();

        // Spring Securityの機能を利用してログアウト．ログアウト時は http://localhost:8000/ に戻る
        http.logout().logoutSuccessUrl("/");
    }

}
