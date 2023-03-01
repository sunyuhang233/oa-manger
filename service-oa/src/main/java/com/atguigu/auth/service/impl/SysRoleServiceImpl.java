package com.atguigu.auth.service.impl;


import com.atguigu.auth.mapper.SysRoleMapper;
import com.atguigu.auth.service.SysRoleService;
import com.atguigu.auth.service.SysUserRoleService;
import com.atguigu.model.system.SysRole;
import com.atguigu.model.system.SysUserRole;
import com.atguigu.vo.system.AssginRoleVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Ahang
 * @version 1.0
 * @description TODO
 * @date 2023/2/28 19:46
 */


@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Resource
    private SysUserRoleService sysUserRoleService;

    /***
     * @description 根据用户id获取角色列表
     * @param userId 用户id
     * @return
     */
    @Override
    public Map<String, Object> selectRoleByUserId(Long userId) {
        HashMap<String, Object> map = new HashMap<>();
        // 1.查询所有角色
        List<SysRole> allRole = this.list();
        // 2.根据用户id查询用户角色关系表
        LambdaQueryWrapper<SysUserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUserRole::getUserId, userId);
        List<SysUserRole> sysUserRoleList = sysUserRoleService.list(wrapper);
        // 3.遍历所有角色，判断是否被选中
        List<Long> roleIdsList = sysUserRoleList.stream().map(c -> c.getRoleId()).collect(Collectors.toList());
        ArrayList<SysRole> sysRoles = new ArrayList<>();
        for (SysRole sysRole : allRole) {
            if (roleIdsList.contains(sysRole.getId())) {
                sysRoles.add(sysRole);
            }
        }
        // 4.返回结果
        map.put("assginRoleList", sysRoles);
        map.put("allRolesList", allRole);
        return map;
    }

    /***
     * @description 根据用户分配角色
     * @param assginRoleVo
     * @return
     */
    @Override
    public void doAssign(AssginRoleVo assginRoleVo) {
        //1.根据用户id删除用户角色关系表
        LambdaQueryWrapper<SysUserRole> wrapper = new LambdaQueryWrapper<>();
        sysUserRoleService.remove(wrapper.eq(SysUserRole::getUserId, assginRoleVo.getUserId()));
        //2.根据用户id和角色id添加用户角色关系表
        List<Long> roleIdList = assginRoleVo.getRoleIdList();
        for (Long roleItem : roleIdList) {
            if (StringUtils.isEmpty(roleItem)) {
                continue;
            }
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setRoleId(roleItem);
            sysUserRole.setUserId(assginRoleVo.getUserId());
            sysUserRoleService.save(sysUserRole);
        }
    }
}