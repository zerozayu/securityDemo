package com.zhangyu.securitydemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhangyu.securitydemo.domain.TblUser;
import org.apache.ibatis.annotations.Mapper;

/**
* @author zhangyu
* @description 针对表【TBL_USER】的数据库操作Mapper
* @createDate 2022-02-04 15:29:24
* @Entity com.zhangyu.securitydemo.domain.TblUser
*/
@Mapper
public interface TblUserMapper extends BaseMapper<TblUser> {


}
