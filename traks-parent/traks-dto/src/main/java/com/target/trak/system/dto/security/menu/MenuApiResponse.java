package com.target.trak.system.dto.security.menu;

import java.util.List;

import com.target.trak.system.dto.common.BaseTargetTrakApiResponse;

public class MenuApiResponse extends BaseTargetTrakApiResponse {

	private List<MenuDto> menuItems;

	public List<MenuDto> getMenuItems() {
		return menuItems;
	}

	public void setMenuItems(List<MenuDto> menuItems) {
		this.menuItems = menuItems;
	}
}
