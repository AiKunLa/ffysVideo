package com.ffysVideo.service.impl.user;

import com.ffysVideo.constant.JwtClaimsConstant;
import com.ffysVideo.constant.MessageConstant;
import com.ffysVideo.dto.UserDTO;
import com.ffysVideo.dto.UserLoginDTO;
import com.ffysVideo.entity.LoginUser;
import com.ffysVideo.entity.User;
import com.ffysVideo.exceptions.AccountException;
import com.ffysVideo.mapper.UserMapper;
import com.ffysVideo.service.user.LoginService;
import com.ffysVideo.utils.JwtUtil;
import com.ffysVideo.utils.RedisUtils;
import com.ffysVideo.vo.UserLoginVo;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private JwtUtil jwtUtil;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private UserMapper userMapper;

    @Resource
    private PasswordEncoder passwordEncoder;

    /**
     * 登录验证
     *
     * @param userDTO
     * @return
     */
    @Override
    public UserLoginVo login(UserLoginDTO userDTO) {
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

        String token = jwtUtil.createJWT(claims);

        //将用户信息存入redis中
        redisUtils.set("login:" + loginUser.getUser().getId(), loginUser.getUser());

        //创建返回对象
        return UserLoginVo.builder().id(loginUser.getUser().getId())
                .name(loginUser.getUser().getName())
                .userName(loginUser.getUser().getUsername())
                .token(token).build();
    }

    @Override
    public void logout() {
        //获取SecurityContextHolder 中的用户id
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        //获取当前用户信息
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        User user = loginUser.getUser();

        //从redis中删除
        redisUtils.del("login:" + user.getId());
    }

    /**
     * 用户注册
     *
     * @param userDTO
     */
    @Override
    public void register(UserDTO userDTO) {

        //判断注册信息是否合法
        //姓名
        if (Objects.isNull(userDTO.getName())) {
            throw new AccountException(MessageConstant.NAME_IS_NULL);
        }
        //用户名
        if (Objects.isNull(userDTO.getUsername())) {
            throw new AccountException(MessageConstant.USERNAME_IS_NULL);
        }
        //判断username是否重复
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setStatus(userDTO.getStatus());
        List<User> userList = userMapper.selectByCondition(user);
        if (!userList.isEmpty()) {
            throw new AccountException(MessageConstant.USERNAME_EXISTS);
        }

        //密码
        if (Objects.isNull(userDTO.getPassword())) {
            throw new RuntimeException(MessageConstant.PASSWORD_IS_NULL);
        }
        //手机号
        if (Objects.isNull(userDTO.getPhone())) {
            throw new AccountException(MessageConstant.PHONE_IS_NULL);
        }
        //存储 将密码加密存储

        BeanUtils.copyProperties(userDTO, user);
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);

        userMapper.add(user);

    }
}
