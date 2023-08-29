package com.xiaoxin.Country.service;

import com.xiaoxin.Country.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xiaoxin
 * @since 2023-08-29
 */
public interface IUserService extends IService<User> {

    User login(User user);

    Boolean register(User user);
}
