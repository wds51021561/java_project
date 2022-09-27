package com.javasm.controller;

import com.javasm.common.http.AxiosResult;
import com.javasm.common.http.EnumStatus;
import com.javasm.common.util.UploadUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.Part;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("common")
public class UpLoadController {

    /**
     *上传图片
     */
    @PostMapping("upload")
    public AxiosResult<String> upload(@RequestPart Part file) throws IOException {
        //判断是不是图片
        BufferedImage read = ImageIO.read(file.getInputStream());
        if (read==null){
            //上传的不是一个图片
            return AxiosResult.error(EnumStatus.IMG_NO_UPLOAD);
        }
        //判断上传图片格式对不对
        String filenameExtension = StringUtils.getFilenameExtension(file.getSubmittedFileName());
        if (!"jpg".equals(filenameExtension) && !"png".equals(filenameExtension)) {
            //上传格式不正确
            return AxiosResult.error(EnumStatus.IMG_NO);

        }
        //判断上传文件多大
        long size = file.getSize()/1024;
        if (size>200){
            //上传文件太大
            return AxiosResult.error(EnumStatus.IMG_NO_MAX);
        }

        String upload = UploadUtils.upload(file);
        return AxiosResult.success(upload);
    }
}
