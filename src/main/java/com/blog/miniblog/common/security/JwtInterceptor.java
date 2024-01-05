//from: https://juejin.cn/post/7225478065391927356#heading-6

package com.blog.miniblog.common.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.blog.miniblog.entity.User;
import com.blog.miniblog.service.UserService;
import com.blog.miniblog.common.exception.TokenException;

@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws TokenException {
        String token = request.getHeader("token");
        // 如果不是映射到方法直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        // 执行认证
        if (token == null || token.isBlank()) {
            throw new TokenException("无token，请重新登录");
        }
        // 获取 token 中的 user id
        String name;
        try {
            name = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            throw new TokenException("token验证失败，请重新登录");
        }
        // 根据token中的userid查询数据库
        User user = userService.getUserWithPassword(name);
        if (user == null) {
            throw new TokenException("用户不存在，请重新登录");
        }
        // 用户密码加签验证 token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try {
            jwtVerifier.verify(token); // 验证token
        } catch (JWTVerificationException e) {
            throw new TokenException("token验证失败，请重新登录");
        }
        return true;
    }
}
