package com.xiaoxin.Country.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaoxin.Country.entity.User;
import com.xiaoxin.Country.exception.ServiceException;
import com.xiaoxin.Country.mapper.UserMapper;
import com.xiaoxin.Country.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
//用户账号冻结
    @Override
    public User toggleAccountFreeze(User user) {
        User dbUser = getOne(new QueryWrapper<User>().eq("username", user.getUsername()));
        log.info("dbUser: " + dbUser.getIsDelete());
        log.info("user: " + user.getIsDelete());
        if (dbUser.getIsDelete() == 1) {
            user.setIsDelete(0);
        } else if (dbUser.getIsDelete() == 0) {
            user.setIsDelete(1);
        }

        // 保存修改后的user对象到数据库
        saveOrUpdate(user);
        return user;
    }

    @Override
    public boolean updatePassword(User user) {
        User dbUser = getOne(new QueryWrapper<User>().eq("username", user.getUsername()));
        if (!user.getIdcard().equals(dbUser.getIdcard())) {
            throw new ServiceException("身份证号码输入错误，无法重置密码");
        }
        else{
            updateById(user);
        }
        return true;

    }



    public String generateUniqueUid() {
        // 生成UUID并将其转换为32位字符串
        String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 32);
        return uuid;
    }
}
