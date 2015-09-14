package com.target.trak.system.service;

import com.target.trak.system.service.dto.security.menu.MenuApiRequest;
import com.target.trak.system.service.dto.security.menu.MenuApiResponse;

public interface MenuService {

	
	public MenuApiResponse getMenuItemsForUser(final MenuApiRequest request);
}
