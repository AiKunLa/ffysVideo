package com.ffysVideo.service.user;

import com.ffysVideo.dto.UserDTO;
import com.ffysVideo.dto.UserLoginDTO;
import com.ffysVideo.vo.UserLoginVo;

public interface LoginService {
    /**
     * 登录
     *
     * @param userDTO
     * @return
     */
    UserLoginVo login(UserLoginDTO userDTO);

    /**
     * 退出登录
     */
    void logout();

    /**
     * 用户注册
     * @param userDTO
     */
    void register(UserDTO userDTO);
}
