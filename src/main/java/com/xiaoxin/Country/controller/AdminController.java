package com.xiaoxin.Country.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelWriter;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletOutputStream;
import java.net.URLEncoder;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.io.InputStream;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaoxin.Country.common.Result;
import org.springframework.web.multipart.MultipartFile;
import com.xiaoxin.Country.service.IAdminService;
import com.xiaoxin.Country.entity.Admin;

import org.springframework.web.bind.annotation.RestController;

/**
* <p>
*  前端控制器
* </p>
*
* @author xiaoxin
* @since 2023-09-08
*/
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private IAdminService adminService;

    @PostMapping
    @SaCheckPermission("admin.add")
    public Result save(@RequestBody Admin admin) {
        adminService.save(admin);
        return Result.success();
    }

    @PutMapping
    @SaCheckPermission("admin.edit")
    public Result update(@RequestBody Admin admin) {
        adminService.updateById(admin);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @SaCheckPermission("admin.delete")
    public Result delete(@PathVariable Integer id) {
        adminService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")
    @SaCheckPermission("admin.deleteBatch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        adminService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    @SaCheckPermission("admin.list")
    public Result findAll() {
        return Result.success(adminService.list());
    }

    @GetMapping("/{id}")
    @SaCheckPermission("admin.list")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(adminService.getById(id));
    }

    @GetMapping("/page")
    @SaCheckPermission("admin.list")
    public Result findPage(@RequestParam(defaultValue = "") String name,
                           @RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<Admin>().orderByDesc("id");
        queryWrapper.like(!"".equals(name), "name", name);
        return Result.success(adminService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

    /**
    * 导出接口
    */
    @GetMapping("/export")
    @SaCheckPermission("admin.export")
    public void export(HttpServletResponse response) throws Exception {
        // 从数据库查询出所有的数据
        List<Admin> list = adminService.list();
        // 在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);

        // 一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list, true);

        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("Admin信息表", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();

    }

    /**
    * excel 导入
    * @param file
    * @throws Exception
    */
    @PostMapping("/import")
    @SaCheckPermission("admin.import")
    public Result imp(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        // 通过 javabean的方式读取Excel内的对象，但是要求表头必须是英文，跟javabean的属性要对应起来
        List<Admin> list = reader.readAll(Admin.class);

        adminService.saveBatch(list);
        return Result.success();
    }

}
