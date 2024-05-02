package com.ffysVideo.filter;

import com.ffysVideo.constant.JwtClaimsConstant;
import com.ffysVideo.entity.LoginUser;
import com.ffysVideo.entity.User;
import com.ffysVideo.properties.JwtProperties;
import com.ffysVideo.utils.JwtUtil;
import com.ffysVideo.utils.RedisUtils;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.security.sasl.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Objects;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Resource
    private JwtProperties jwtProperties;

    @Resource
    private RedisUtils redisUtils;

    /**
     * jwt 请求头校验
     *
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取请求同中的token
        String token = request.getHeader(jwtProperties.getUserTokenName());

        //判断是否为空  为空就放行 给后面的过滤器
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }
        //解析token
        try {
            //解析token
            Claims claims = JwtUtil.parseJWT(jwtProperties.getUserSecretKey(), token);
            //获取token中的userId
            String userId = claims.get(JwtClaimsConstant.USER_ID).toString();

            //从redis获取用户信息
            User user = (User) redisUtils.get("login:" + userId);

            if (Objects.nonNull(user)) { //不为空将 将用户信息存入SecurityContextHandler
                //
                LoginUser loginUser = new LoginUser(user);

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(loginUser, null, null);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

            //放行 若SecurityContextHandler 中没有信息，在后面的过滤器中无法通过
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            response.setStatus(401);
            throw new AuthenticationException("用户未登录");
        }

    }
}
