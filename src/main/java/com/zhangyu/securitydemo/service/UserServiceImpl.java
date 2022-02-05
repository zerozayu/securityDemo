package com.zhangyu.securitydemo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhangyu.securitydemo.domain.TblUser;
import com.zhangyu.securitydemo.mapper.TblUserMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserDetailsService {
    private final PasswordEncoder pw;
    private final TblUserMapper userMapper;

    public UserServiceImpl(PasswordEncoder pw, TblUserMapper userMapper) {
        this.pw = pw;
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据用户名查询数据库中的用户名密码
        QueryWrapper<TblUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        TblUser tblUser = userMapper.selectOne(wrapper);

        if (null == tblUser){
            throw new UsernameNotFoundException("用户不存在");
        }else {
            List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                    .commaSeparatedStringToAuthorityList("admin,normal");
            return new User(tblUser.getUsername(), pw.encode(tblUser.getPassword()), grantedAuthorities);
        }

        // 1.查询数据库查询用户名是否存在，如果不存在就抛出UsernameNotFoundException
        // if(!"admin".equals(username)){
        //     throw new UsernameNotFoundException("用户不存在");
        // }
        // 2.把查询出来的密码(注册时已经加密过)进行解析，或直接把密码放入构造方法中
        // String password = pw.encode("123");
        // return new User(username, password, AuthorityUtils
        //         .commaSeparatedStringToAuthorityList("admin,normal"));
    }
}
