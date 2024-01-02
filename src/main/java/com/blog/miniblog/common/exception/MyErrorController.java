package com.blog.miniblog.common.exception;

import com.blog.miniblog.common.result.Result;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//GlobalExceptionHandler无法处理404、500等错误，交给MyErrorController处理
@RestController
public class MyErrorController implements ErrorController {
    @RequestMapping(value = "/error")
    public Result error(HttpServletResponse rs, Exception e) {
        String status = Integer.toString(rs.getStatus());
        return Result.fail(status);
    }
}
