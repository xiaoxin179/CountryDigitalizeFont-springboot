package com.xiaoxin.Country.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaoxin.Country.entity.User;
import com.xiaoxin.Country.exception.ServiceException;
import com.xiaoxin.Country.mapper.UserMapper;
import com.xiaoxin.Country.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.UUID;

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

    @Override
    public User login(User user) {
        User dbUser;
        try {
            dbUser=getOne(new QueryWrapper<User>().eq("username", user.getUsername()));

        } catch (Exception e) {
            throw new RuntimeException("数据库异常");
        }
        if(dbUser == null){
            throw new ServiceException("用户未注册");
        }
        if (!dbUser.getPassword().equals(user.getPassword())) {
            throw new ServiceException("账号或者密码错误");
        }
        return dbUser;

    }

    @Override
    public Boolean register(User user) {
        User dbUser;
        try {
            dbUser = getOne(new QueryWrapper<User>().eq("username", user.getUsername()));
        } catch (Exception e) {
            throw new RuntimeException("数据库异常");
        }
        user.setFocus(0);
        String uniqueUid = generateUniqueUid();
        user.setUserid(uniqueUid);
        if (dbUser != null) {
            throw new ServiceException("用户名已经被注册");
        } else {
            boolean isSave = save(user);
            return isSave;
        }
    }
    public String generateUniqueUid() {
        // 生成UUID并将其转换为32位字符串
        String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 32);
        return uuid;
    }
}
