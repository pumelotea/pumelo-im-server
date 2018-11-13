package io.pumelo.controller;

import io.pumelo.common.basebean.PermissionType;
import io.pumelo.permission.annotation.PermissionRegister;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 用来配置基础外层菜单数据
 * author: pumelo
 * 2018/3/23
 */
@RestController
@ApiIgnore
public class ModuleController {


    @PermissionRegister(key = "user",name = "用户",type = PermissionType.MODULE)
    @GetMapping("_/module/user")
    public void user(){

    }

    @PermissionRegister(key = "test",name = "测试",type = PermissionType.MODULE)
    @GetMapping("_/module/test")
    public void test(){

    }

    @PermissionRegister(key = "device",name = "设备",type = PermissionType.MODULE)
    @GetMapping("_/module/device")
    public void device(){

    }

    @PermissionRegister(key = "area",name = "区域",type = PermissionType.MODULE)
    @GetMapping("_/module/area")
    public void area(){

    }

    @PermissionRegister(key = "dashboard",name = "监控面板",type = PermissionType.MODULE)
    @GetMapping("_/module/dashboard")
    public void dashboard(){

    }


}
