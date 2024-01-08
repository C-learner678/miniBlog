//from: https://juejin.cn/post/7225478065391927356#heading-6

package com.blog.miniblog.common.security;

import java.util.Date;
import java.util.Calendar;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.blog.miniblog.entity.User;
import com.blog.miniblog.service.UserService;

@Component
public class JWTUtils {
    private static UserService staticUserService;

    @Resource
    private UserService userService;

    @PostConstruct
    public void setUserService() {
        staticUserService = userService;
    }

    public static String getToken(Long id, String password) {
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.HOUR_OF_DAY, 1);
        Date modifiedDate = calendar.getTime();
        return JWT.create().withAudience(String.valueOf(id)) // 将 user的id 保存到 token 里面,作为载荷
                .withExpiresAt(modifiedDate) // 2小时后token过期
                .sign(Algorithm.HMAC256(password)); // 以 password 作为 token 的密钥
    }

    public static User getCurrentUser() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("token");
            if (token != null && !token.isBlank()) {
                Long id = Long.valueOf(JWT.decode(token).getAudience().get(0));
                return staticUserService.selectUser(id, false);
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}
