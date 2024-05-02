package com.ffysVideo.service.impl.user;

import com.ffysVideo.entity.LoginUser;
import com.ffysVideo.entity.User;
import com.ffysVideo.mapper.UserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {

    @Resource
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.getByUserName(username);

//        //判断是否有该用户
//        if (user == null) {
//            throw new RuntimeException("没有该用户");
//        }
        //权限 角色设置

        return new LoginUser(user);
    }
}
