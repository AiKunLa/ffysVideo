package com.ffysVideo.service.impl;

import com.ffysVideo.constant.JwtClaimsConstant;
import com.ffysVideo.dto.UserDTO;
import com.ffysVideo.entity.LoginUser;
import com.ffysVideo.properties.JwtProperties;
import com.ffysVideo.service.LoginService;
import com.ffysVideo.utils.JwtUtil;
import com.ffysVideo.utils.RedisUtils;
import com.ffysVideo.vo.UserLoginVo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private JwtProperties jwtProperties;


    @Override
    public UserLoginVo login(UserDTO userDTO) {
        //封装成 authentication对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword());
        //认证
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //判断认证是否成功
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("登录失败！");
        }
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        //成功后生产jwt令牌
        Map<String, Object> claims = new HashMap<>();
        //将用户id 放入jwt令牌中
        claims.put(JwtClaimsConstant.USER_ID, loginUser.getUser().getId());

        String token = JwtUtil.createJWT(jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(), claims);




        //创建返回对象
        return UserLoginVo.builder().id(loginUser.getUser().getId())
                .name(loginUser.getUser().getName())
                .userName(loginUser.getUser().getUserName())
                .token(token).build();
    }
}
