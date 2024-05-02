package com.ffysVideo.config;

import com.ffysVideo.config.filter.JwtAuthenticationTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Resource
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    //设置加密方式
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }


    //    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//    }


    //     过滤配置 登录设置
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling().accessDeniedPage("/unanth.html"); //没有权限转跳的页面

        http.formLogin()
//                .loginPage("/login.html") //登录页面
                .loginProcessingUrl("/login")//登录路径
//                .defaultSuccessUrl("/user/index").permitAll()//登录成功后，转跳路径
                .and().authorizeRequests()
                .antMatchers("/user/**",
                        "/login.html",
                        "/user/register/*",
                        "/swagger-ui.html", "/swagger-resources/**", "/v2/api-docs", "/doc.html").permitAll() //可直接访问的路径
//                .antMatchers("/user/admin").hasAuthority("admin") //有admin权限才能访问 该路径  权限设置在 UserDetailService中
//                .antMatchers("/user/forward").hasAnyAuthority("admin,user,other") //有其中任意一个权限都能访问
//                .antMatchers("/user/list").hasRole("sale") //该角色能访问 要设置前缀  Role_sale
                .anyRequest().authenticated()
                .and().csrf().disable();//关闭csrf防护

        //设置自定义 过滤器的位置     过滤器链：UsernamePasswordAuthenticationFilter —— ExceptionFilter ——  FilterSecurityInterceptor
        // 分别是 用户信息验证  异常处理  security验证（统一验证）
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
