package com.zhangyu.securitydemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhangyu.securitydemo.domain.TblUser;
import com.zhangyu.securitydemo.service.TblUserService;
import com.zhangyu.securitydemo.mapper.TblUserMapper;
import org.springframework.stereotype.Service;

/**
* @author zhangyu
* @description 针对表【TBL_USER】的数据库操作Service实现
* @createDate 2022-02-04 15:29:24
*/
@Service
public class TblUserServiceImpl extends ServiceImpl<TblUserMapper, TblUser>
implements TblUserService{

}
