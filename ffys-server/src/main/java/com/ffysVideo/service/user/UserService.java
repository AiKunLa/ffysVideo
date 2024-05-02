package com.ffysVideo.service.user;

import com.ffysVideo.entity.User;

public interface UserService {
    /**
     * 通过id查询用户
     * @param id
     * @return
     */
    User getById(Long id);
}
