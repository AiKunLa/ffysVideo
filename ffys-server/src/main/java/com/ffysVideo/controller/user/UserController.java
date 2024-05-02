package com.ffysVideo.controller.user;

import com.ffysVideo.entity.User;
import com.ffysVideo.result.ResultData;
import com.ffysVideo.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
@Api(tags = "用户相关接口")
public class UserController {

    @Resource
    private UserService userService;



    @GetMapping("/{id}")
    @ApiOperation(value = "通过id获取用户信息")
    public ResultData<User> getById(@PathVariable("id") Long id) {
        User user = userService.getById(id);
        return ResultData.success(user);
    }
}
