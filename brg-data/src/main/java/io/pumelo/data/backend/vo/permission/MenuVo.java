package io.pumelo.data.backend.vo.permission;

import io.pumelo.data.backend.struct.PermissionUnit;

import javax.persistence.Column;
import java.util.List;

/**
 * 菜单数据结构
 */
public class MenuVo {
    @Column
    private List<PermissionUnit> menu;

    public MenuVo(List<PermissionUnit> menu) {
        this.menu = menu;
    }

    public List<PermissionUnit> getMenu() {
        return menu;
    }

    public void setMenu(List<PermissionUnit> menu) {
        this.menu = menu;
    }
}
