package com.xiaoxin.datinghubback.service.impl;

import com.xiaoxin.datinghubback.entity.User;
import com.xiaoxin.datinghubback.mapper.UserMapper;
import com.xiaoxin.datinghubback.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xiaoxin
 * @since 2023-08-29
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
