package com.zhangyu.securitydemo.config;

import com.zhangyu.securitydemo.handler.MyAuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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
                // 使用successForwardUrl()时表示成功后转发请求到地址。内部是通过 successHandler() 方法进行控制成功后交给哪个类进行处理，
                // ForwardAuthenticationSuccessHandler内部就是最简单的请求转发。由于是请求转发，当遇到需要 跳转到站外或在前后端分离的
                // 项目中就无法使用了。
                // .successForwardUrl("/toMain")
                // 跟.successForwardUrl("/toMain")不能共存
                .successHandler(new MyAuthenticationSuccessHandler("https://www.baidu.com"))

                // 登录失败后的跳转页面，POST请求
                .failureForwardUrl("/toError")
                .usernameParameter("myUsername")
                .passwordParameter("myPassword")
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
