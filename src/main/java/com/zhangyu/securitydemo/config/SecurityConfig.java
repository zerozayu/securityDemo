package com.zhangyu.securitydemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // 自定义登录页面
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 表单提交
        http.formLogin()
                // 自定义登录界面
                .loginPage("/login.html")
                // 当发现/login时认为是登录，必须和表单提交的地址一样。去执行UserServiceImpl
                .loginProcessingUrl("/login")
                // 登录成功后跳转页面，POST请求
                .successForwardUrl("/toMain")
                // 登录失败后的跳转页面，POST请求
                .failureForwardUrl("/toError")
        ;

        http.authorizeRequests()
                .antMatchers("/login.html", "/error.html").permitAll()
                .anyRequest().authenticated();

        http.csrf().disable();
    }

    /**
     *  Spring Security 要求:当进行自定义登录逻辑时容器内必须有 PasswordEncoder 实例。所以不能直接 new 对象。
     * @return
     */
    @Bean
    public PasswordEncoder getPw(){
        return new BCryptPasswordEncoder();
    }
}
