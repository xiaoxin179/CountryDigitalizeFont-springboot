package com.xiaoxin.Country.service;

import com.xiaoxin.Country.entity.Admin;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xiaoxin
 * @since 2023-09-07
 */
public interface IAdminService extends IService<Admin> {

    Admin adminLogin(Admin admin);
}
