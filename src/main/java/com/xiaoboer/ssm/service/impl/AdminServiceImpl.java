package com.xiaoboer.ssm.service.impl;

import com.xiaoboer.ssm.mapper.AdminMapper;
import com.xiaoboer.ssm.pojo.Admin;
import com.xiaoboer.ssm.pojo.AdminExample;
import com.xiaoboer.ssm.service.AdminService;
import com.xiaoboer.ssm.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminMapper adminMapper;

    /**
     * 根据传入的信息到DB中查询响应的用户对象
     * @param name
     * @param pwd
     * @return
     */
    @Override
    public Admin login(String name, String pwd) {
        //如果有条件，一定创建AdminExample的对象，来封装条件
        AdminExample adminExample = new AdminExample();
        //如何添加条件：select * from admin where a_name = 'admin'

        //添加用户名a_name的条件
        adminExample.createCriteria().andANameEqualTo(name);

        List<Admin> list = adminMapper.selectByExample(adminExample);

        if (list.size() >0){
            Admin admin = list.get(0);

            if (admin.getaPass().equals(MD5Util.getMD5(pwd))){
                return admin;
            }
        }

        return null;
    }
}
