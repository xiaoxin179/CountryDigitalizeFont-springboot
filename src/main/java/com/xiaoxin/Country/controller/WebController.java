package com.xiaoxin.Country.controller;

import com.xiaoxin.Country.common.Result;
import com.xiaoxin.Country.entity.Admin;
import com.xiaoxin.Country.entity.User;
import com.xiaoxin.Country.service.IAdminService;
import com.xiaoxin.Country.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

//对当前接口类设置标签，本接口类为存放不需要权限验证的接口类，例如登录注册等等
@Api(tags = "无权限接口列表")
@RestController
public class WebController {
    @Resource
    IUserService userService;
    @Resource
    IAdminService adminService;
    @GetMapping(value = "/")
    @ApiOperation(value = "版本校验接口")
    public String version() {
        String ver = "XIAOXINDatingHub-back-0.0.1-SNAPSHOT";  // 应用版本号
        Package aPackage = WebController.class.getPackage();
        String title = aPackage.getImplementationTitle();
        String version = aPackage.getImplementationVersion();
        if (title != null && version != null) {
            ver = String.join("-", title, version);
        }
        return ver;
    }

    @PostMapping("/login")
    @ApiOperation(value = "用户登录接口")
    public Result login(@RequestBody User user) {
        User res = userService.login(user);
        return Result.success(res);
    }
    @PostMapping("/register")
    @ApiOperation(value = "用户注册接口")
    public Result register(@RequestBody User user) {
        Boolean res = userService.register(user);
        return Result.success();
    }
    /*
    *管理员登录接口
     */
    @PostMapping("/adminLogin")
    @ApiOperation(value = "用户登录接口")
    public Result adminLogin(@RequestBody Admin admin) {
        Admin  res =adminService.adminLogin(admin);
        return Result.success(res);
    }
}
