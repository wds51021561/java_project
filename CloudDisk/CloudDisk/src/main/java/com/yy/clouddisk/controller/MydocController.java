package com.yy.clouddisk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MydocController {
    /**
     * 显示当前路径下的所有文件和子目录
     */
    @GetMapping("/docs*")
    public ModelAndView list(@RequestParam("current") String pdir, HttpServletRequest request) {
        try {
            pdir = URLDecoder.decode(new String(pdir.getBytes("ISO-8859-1"), "UTF-8"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        String userid = request.getRemoteUser();
        List<File> me = new ArrayList<>();
        List<String> subDir = new ArrayList<>();
        File f;
        if (pdir.equals("root")) {
            f = new File("d:/user/" + userid);
            if (!f.exists()) {
                f.mkdir();
            }
        } else {
            f = new File("d:/user/" + userid + "/" + pdir)
        }

        File[] files = f.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                me.add(file);
            } else if (file.isDirectory()) {
                subDir.add(file.getName());
            }
        }
        ModelMap modelMap = new ModelMap();
        modelMap.put("files",me);
        modelMap.put("dirs",subDir);
        modelMap.put("current",pdir);
        return new ModelAndView("/filelist",modelMap);
    }
}
