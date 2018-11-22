package com.zz.miaosha.service;

import com.zz.miaosha.dao.UserDao;
import com.zz.miaosha.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public User getById(int id){
        return userDao.getByid(id);
    }

    @Transactional
    public boolean ts() {
        User u1 = new User();
        u1.setId(2);
        u1.setName("222");
        userDao.insert(u1);

        User u2 = new User();
        u2.setId(1);
        u2.setName("333");
        userDao.insert(u2);

        return true;
    }
}
