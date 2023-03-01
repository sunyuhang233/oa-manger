package com.atguigu.auth.service;

import com.atguigu.model.system.SysUser;
import com.atguigu.vo.system.SysUserQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

public interface SysUserService extends IService<SysUser> {
    /***
     * @description 修改状态
     * @param id
     * @param status
     * @return
     */
    void updateStatus(Long id, Integer status);

    Map<String, Object> getUserInfo(String username);
}