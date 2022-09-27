package com.javasm.controller;

import com.alibaba.excel.EasyExcel;
import com.javasm.common.http.AxiosResult;
import com.javasm.common.page.PageResult;
import com.javasm.common.perm.HasPerm;
import com.javasm.common.util.TreeUtils;
import com.javasm.common.valid.addGroup;
import com.javasm.controller.base.BaseController;
import com.javasm.domin.criteria.AdminCriteria;
import com.javasm.domin.entity.Admin;
import com.javasm.domin.excel.AdminExcel;
import com.javasm.domin.vo.AdminVo;
import com.javasm.domin.vo.DeptVo;
import com.javasm.service.AdminService;
import com.javasm.service.DeptService;
import com.javasm.transfer.AdminExcelTransfer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("admin")
@RequiredArgsConstructor//和@Autowired效果一样
public class AdminController extends BaseController {


    private final AdminService adminService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 分页条件查询
     */
    @GetMapping
    public AxiosResult<PageResult<AdminVo>> list(AdminCriteria adminCriteria) {
        System.out.println(adminCriteria.toString());

        PageResult<AdminVo> list = adminService.searchPage(adminCriteria);
        return AxiosResult.success(list);
    }

    /**
     * 根据id查询
     */
    @GetMapping("{id}")
    public AxiosResult<Admin> findById(@PathVariable Long id) {
        Admin byId = adminService.getAdminAndRoleIdsById(id);
        return AxiosResult.success(byId);
    }

    /**
     * 添加
     */
    @PostMapping
    @HasPerm(perm = "admin:add")
    public AxiosResult<Void> add(@Validated(addGroup.class) @RequestBody Admin admin) {
/*        @Bean
        public BCryptPasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }*/
        //添加默认的密码  使用BCryptPasswordEncoder 方式加密
        admin.setAdminPwd(bCryptPasswordEncoder.encode("123456"));
        //默认不是超级管理员
        admin.setIsAdmin(false);
        //添加员工并添加角色
        return toAxios(adminService.saveAdminAndRoles(admin));

        //如果表单验证有这个异常
  /*      if (bindingResult.hasFieldErrors()){
            //找到这个异常打印
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            fieldErrors.forEach(fieldError -> {
                //打印当前字段
                System.out.println(fieldError.getField());
                //打印默认错误
                System.out.println(fieldError.getDefaultMessage());
            });
        }

        return AxiosResult.success();*/
    }

    /**
     * 修改
     */
    @PutMapping
    @HasPerm(perm = "admin:edit")
    public AxiosResult<Void> update(@RequestBody Admin admin) {
        //修改员工信息以及角色信息
        return toAxios(adminService.updateAdminAndRoles(admin));
    }

    /**
     * 删除
     */
    @DeleteMapping("{id}")
    @HasPerm(perm = "admin:delete")
    public AxiosResult<Void> delete(@PathVariable Long id) {
        return toAxios(adminService.deleteById(id));
    }


    /**
     * 批量删除
     */
    @DeleteMapping("batch/{ids}")
    @HasPerm(perm = "admin:batch")
    public AxiosResult<Void> batch(@PathVariable List<Long> ids) {
        return toAxios(adminService.batchDeleteByIds(ids));
    }

    /**
     * 员工修改 部门回显数据
     */
    private final DeptService deptService;

    @GetMapping("findParentByDeptId/{id}")
    public AxiosResult<List<DeptVo>> findParentById(@PathVariable Long id) {
        //传过去部门id  并新建一个集合对象  不用重复的new
        List<DeptVo> list = deptService.getDeptVoTree(id, new ArrayList<>());
        //返回树状结构
        List<DeptVo> deptVos = TreeUtils.bulidTree(list);
        return AxiosResult.success(deptVos);
    }

    /**
     * 文件的导出 使用alibaba的easyexcel方式
     */
    private final AdminExcelTransfer adminExcelTransfer;

    @GetMapping("export")
    //@HasPerm(perm = "admin:export")
    public ResponseEntity<byte[]> export() throws UnsupportedEncodingException {

        //拿到数据
        List<Admin> list = adminService.list();
        List<AdminExcel> adminExcels = adminExcelTransfer.toVO(list);
        //导出头像
        adminExcels.forEach(admin -> {
            try {
                admin.setUrl(new URL(admin.getAdminAvatar()));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        });

        //输出流
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        //写出
        EasyExcel.write(out, AdminExcel.class).sheet("员工信息表").doWrite(adminExcels);
        //写出
        byte[] bytes = out.toByteArray();
        //固定三写法
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentDispositionFormData("attachement", URLEncoder.encode("员工信息表.xlsx", "utf-8"));
        return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);


    }

}
