package com.zhangyu.securitydemo.config;

import com.zhangyu.securitydemo.handler.MyAccessDeniedHandler;
import com.zhangyu.securitydemo.service.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final DataSource dataSource;
    private final MyAccessDeniedHandler myAccessDeniedHandler;
    private final UserServiceImpl userService;
    private final PersistentTokenRepository persistentTokenRepository;

    public SecurityConfig(DataSource dataSource, MyAccessDeniedHandler myAccessDeniedHandler,
                          UserServiceImpl userService,
                          PersistentTokenRepository persistentTokenRepository) {
        this.dataSource = dataSource;
        this.myAccessDeniedHandler = myAccessDeniedHandler;
        this.userService = userService;
        this.persistentTokenRepository = persistentTokenRepository;
    }

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
                // .successHandler(new MyAuthenticationSuccessHandler("https://www.baidu.com"))
                .successForwardUrl("/toTest")
                // 登录失败后的跳转页面，POST请求
                .failureForwardUrl("/toError")
                .usernameParameter("myUsername")
                .passwordParameter("myPassword")
        ;

        http.exceptionHandling()
                .accessDeniedHandler(myAccessDeniedHandler);

        http.authorizeRequests()
                .antMatchers("/login.html", "/error.html").permitAll()
                // .antMatchers("/test.html").hasAnyAuthority("aa")
                .anyRequest().authenticated();


        // rememberMe功能--->
        // 在springboot高版本会报循环依赖，SecurityConfig 通过构造函数注入 UserDetailsService实例
        // 而 UserDetailsService由通过构造函数注入在SecurityConfig 中声明的PasswordEncoder。
        // http.rememberMe()
        //         // 失效时间，单位秒
        //         .tokenValiditySeconds(120)
        //         // 登陆逻辑交给哪个对象
        //         .userDetailsService(userService)
        //         // 持久层对象
        //         .tokenRepository(persistentTokenRepository);

        http.csrf().disable();
    }

    /**
     * Spring Security 要求:当进行自定义登录逻辑时容器内必须有 PasswordEncoder 实例。所以不能直接 new 对象。
     *
     * @return
     */
    @Bean
    public PasswordEncoder getPw() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public PersistentTokenRepository getPersistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        // 第一次启动后注释掉
        jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }
}
