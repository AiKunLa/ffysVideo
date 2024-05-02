package com.ffysVideo.service;

import com.ffysVideo.dto.UserDTO;
import com.ffysVideo.vo.UserLoginVo;

public interface LoginService {
    /**
     * 登录
     *
     * @param userDTO
     * @return
     */
    UserLoginVo login(UserDTO userDTO);
}
