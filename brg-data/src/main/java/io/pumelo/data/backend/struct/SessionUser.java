package io.pumelo.data.backend.struct;

import java.io.Serializable;
import java.util.List;

/**
 * 会话用户对象
 * author: pumelo
 * 2018/3/29
 */
public class SessionUser<T> implements Serializable {
    private T user;
    private List<PermissionUnit> permission;

    public T getUser() {
        return user;
    }

    public void setUser(T user) {
        this.user = user;
    }

    public List<PermissionUnit> getPermission() {
        return permission;
    }

    public void setPermission(List<PermissionUnit> permission) {
        this.permission = permission;
    }

    public boolean isAccessPermission(String method,String url){
        if(permission == null){
            return false;
        }
        for (PermissionUnit module : permission) {
            if(module.getPermissionBranch()!=null){
                for(PermissionUnit menu : module.getPermissionBranch()) {
                    if(menu.getPermissionBranch()!=null){
                        for(PermissionUnit fun : menu.getPermissionBranch()) {
                            if(fun.getUrl().equals(url)&& fun.getMethod().equals(method)){
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
