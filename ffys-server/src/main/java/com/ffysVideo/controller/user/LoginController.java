package com.ffysVideo.controller.user;

import com.ffysVideo.dto.UserDTO;
import com.ffysVideo.dto.UserLoginDTO;
import com.ffysVideo.result.ResultData;
import com.ffysVideo.service.user.LoginService;
import com.ffysVideo.vo.UserLoginVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@Api(tags = "登录相关接口")
public class LoginController {

    @Resource
    private LoginService loginService;

    /**
     * 用户登录
     *
     * @param userLoginDTO
     * @return
     */
    @PostMapping("/user/login")
    @ApiOperation("登录接口")
    public ResultData<UserLoginVo> login(@RequestBody UserLoginDTO userLoginDTO) {
        //
        UserLoginVo userLoginVo = loginService.login(userLoginDTO);
        return ResultData.success(userLoginVo);
    }


    /**
     * 用户注册
     *
     * @param userDTO
     * @return
     */
    @PostMapping("/user/register")
    @ApiOperation("注册")
    public ResultData register(@RequestBody UserDTO userDTO) {
        log.info("用户注册：{}", userDTO);
        loginService.register(userDTO);
        return ResultData.success();
    }


    /**
     * 退出登录
     *
     * @return
     */
    @GetMapping("/user/logout")
    @ApiOperation("退出登录")
    public ResultData logout() {
        loginService.logout();
        return ResultData.success();
    }

}
