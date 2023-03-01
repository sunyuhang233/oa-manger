package com.atguigu.auth.service;

import com.atguigu.model.system.SysMenu;
import com.atguigu.vo.system.AssginMenuVo;
import com.atguigu.vo.system.RouterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysMenuService extends IService<SysMenu> {
    /***
     * @description 获取树形结构菜单
     * @return
     */
    List<SysMenu> findNodes();

    /***
     * @description 删除菜单
     * @param id
     * @return
    */
    void removeMenuById(Long id);

    /***
     * @description 根据角色获取菜单
     * @param roleId
     * @return
     */
    List<SysMenu> findSysMenuByRoleId(Long roleId);
    /***
     * @description 给角色分配权限
     * @param assignMenuVo
     * @return
     */
    void doAssign(AssginMenuVo assignMenuVo);


    List<RouterVo> findUserMenuList(Long id);

    List<String> findUserPermsList(Long id);
}