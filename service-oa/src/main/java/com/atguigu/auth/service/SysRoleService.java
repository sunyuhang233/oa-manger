package com.atguigu.auth.service;


import com.atguigu.model.system.SysRole;
import com.atguigu.vo.system.AssginRoleVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * @author Ahang
 * @version 1.0
 * @description TODO
 * @date 2023/2/28 20:00
 */


public interface SysRoleService extends IService<SysRole> {
    /***
     * @description 根据用户id获取角色列表
     * @param userId 用户id
     * @return
     */
    Map<String,Object> selectRoleByUserId(Long userId);
    /***
     * @description 根据用户分配角色
     * @param assginRoleVo
     * @return
     */
    void doAssign(AssginRoleVo assginRoleVo);
}
