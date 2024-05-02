package com.ffysVideo.service.impl.user;

import com.ffysVideo.entity.User;
import com.ffysVideo.mapper.UserMapper;
import com.ffysVideo.service.user.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {


    @Resource
    private UserMapper userMapper;

    /**
     * 通过id查询用户
     *
     * @param id
     * @return
     */
    public User getById(Long id) {
        return userMapper.getById(id);
    }
}
