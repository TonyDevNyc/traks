package com.target.trak.system.dto.security;

import org.springframework.core.convert.converter.Converter;

import com.target.trak.system.dto.security.menu.MenuDto;
import com.target.trak.system.entity.Menu;

public class MenuDtoConverter implements Converter<Menu, MenuDto> {

	@Override
	public MenuDto convert(Menu entity) {
		MenuDto dto = new MenuDto();
		dto.setMenuId(entity.getMenuId());
		dto.setParentMenuId(entity.getParentMenuId());
		dto.setItemId(entity.getItemId());
		dto.setDisplayOrder(entity.getDisplayOrder());
		dto.setText(entity.getText());
		dto.setIconCss(entity.getIconCss());
		dto.setPrivilegeNeeded(entity.getPrivilegeNeeded());
		dto.setLink(entity.getLink());
		return dto;
	}

}
