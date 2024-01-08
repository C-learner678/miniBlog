// from: https://blog.csdn.net/yangshuaionline/article/details/100120053

package com.blog.miniblog.controller;

import com.blog.miniblog.common.result.Result;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Slf4j
@RestController
public class ImageController {
    private final static String imagePath = "images";
    @GetMapping("/getImage/{name}")
    public void getImage(@PathVariable String name, HttpServletResponse response) {
        response.setContentType("image/jpeg;charset=utf-8");
        response.setHeader("Content-Disposition", "inline; filename=" + name);
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(Files.readAllBytes(Paths.get(imagePath).resolve(name)));
            outputStream.flush();
            outputStream.close();
        }catch (IOException e){
            log.error("找不到文件");
        }
    }
    @PostMapping("/upload")
    public Result upload(@RequestParam("file") MultipartFile file) {
        try {
            String originalFileName = file.getOriginalFilename();
            Assert.notNull(originalFileName, "文件名缺失");
            String suffix = originalFileName.substring(originalFileName.lastIndexOf("."));
            if(!suffix.equals(".jpg") && !suffix.equals(".jpeg")){
                return Result.fail("仅支持jpg、jpeg格式");
            }
            byte[] bytes = file.getBytes();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            Date date = new Date();
            Random random = new Random();
            int i = random.nextInt(100000);
            String newFileName = dateFormat.format(date) + i + suffix;
            Path path = Paths.get(imagePath + "/" + newFileName);
            Files.write(path, bytes);
            return Result.success(newFileName);
        } catch (IOException e) {
            log.error("图片上传失败");
            return Result.fail("图片上传失败");
        }
    }
}
