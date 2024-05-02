package com.ffysVideo.controller;

import com.ffysVideo.dto.UserDTO;
import com.ffysVideo.result.ResultData;
import com.ffysVideo.service.user.LoginService;
import com.ffysVideo.vo.UserLoginVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class LoginController {

    @Resource
    private LoginService loginService;

    /**
     * 用户登录
     *
     * @param userDTO
     * @return
     */
    @PostMapping("/user/login")
    public ResultData<UserLoginVo> login(@RequestBody UserDTO userDTO) {
        //
        UserLoginVo userLoginVo = loginService.login(userDTO);
        return ResultData.success(userLoginVo);
    }

    /**
     * 退出登录
     *
     * @return
     */
    @GetMapping("/user/logout")
    public ResultData logout() {
        loginService.logout();
        return ResultData.success();
    }

}
