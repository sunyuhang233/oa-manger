package com.atguigu.auth.service.impl;

import com.atguigu.auth.mapper.SysUserMapper;
import com.atguigu.auth.service.SysMenuService;
import com.atguigu.auth.service.SysUserService;
import com.atguigu.model.system.SysUser;
import com.atguigu.vo.system.RouterVo;
import com.atguigu.vo.system.SysUserQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysMenuService sysMenuService;

    /***
     * @description 修改状态
     * @param id
     * @param status
     * @return
    */
    @Override
    public void updateStatus(Long id, Integer status) {
        SysUser sysUser = baseMapper.selectById(id);
        if(status.intValue() == 1) {
            sysUser.setStatus(status);
        } else {
            sysUser.setStatus(0);
        }
        baseMapper.updateById(sysUser);
    }

        @Override
        public Map<String, Object> getUserInfo(String username) {
            Map<String, Object> result = new HashMap<>();
            LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SysUser::getUsername, username);
            SysUser sysUser = baseMapper.selectOne(wrapper);

            //根据用户id获取菜单权限值
            List<RouterVo> routerVoList = sysMenuService.findUserMenuList(sysUser.getId());
            //根据用户id获取用户按钮权限
            List<String> permsList = sysMenuService.findUserPermsList(sysUser.getId());

            result.put("name", sysUser.getName());
            result.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
            //当前权限控制使用不到，我们暂时忽略
            result.put("roles",  new HashSet<>());
            result.put("buttons", permsList);
            result.put("routers", routerVoList);
            return result;
        }
    }