package com.xiaoxin.Country.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaoxin.Country.entity.Admin;
import com.xiaoxin.Country.entity.User;
import com.xiaoxin.Country.exception.ServiceException;
import com.xiaoxin.Country.mapper.AdminMapper;
import com.xiaoxin.Country.service.IAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xiaoxin
 * @since 2023-09-08
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Override
    public Admin adminLogin(Admin admin) {
        Admin dbAdmin;
        try {
            dbAdmin=getOne(new QueryWrapper<Admin>().eq("username", admin.getUsername()));

        } catch (Exception e) {
            throw new RuntimeException("数据库异常");
        }
        if(dbAdmin == null){
            throw new ServiceException("该管理员未注册");
        }
        if (!dbAdmin.getPassword().equals(admin.getPassword())) {
            throw new ServiceException("账号或者密码错误");
        }
        return dbAdmin;
    }
}
