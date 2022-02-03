package com.zhangyu.securitydemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserDetailsService {
    private final PasswordEncoder pw;

    public UserServiceImpl(PasswordEncoder pw) {
        this.pw = pw;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1.查询数据库查询用户名是否存在，如果不存在就抛出UsernameNotFoundException
        if(!"admin".equals(username)){
            throw new UsernameNotFoundException("用户不存在");
        }
        // 2.把查询出来的密码(注册时已经加密过)进行解析，或直接把密码放入构造方法中
        String password = pw.encode("123");
        return new User(username, password, AuthorityUtils
                .commaSeparatedStringToAuthorityList("admin,normal"));
    }
}
