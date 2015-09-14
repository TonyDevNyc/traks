package com.target.trak.system.persistence.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.target.trak.system.entity.Menu;
import com.target.trak.system.persistence.MenuDao;

public class MenuDaoImpl implements MenuDao {

	private NamedParameterJdbcTemplate menuTemplate;

	private Properties menuQueries;
	
	public MenuDaoImpl(DataSource dataSource) {
		menuTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	@Override
	public List<Menu> selectMenuItemsByPrivileges(final List<Long> privilegeIds) {
		String sql = menuQueries.getProperty("selectMenuByPrivilegesSql");
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("privilegeIds", privilegeIds);
		return menuTemplate.query(sql, params, new MenuRowMapper());
	}

	public void setMenuQueries(Properties menuQueries) {
		this.menuQueries = menuQueries;
	}

	private final class MenuRowMapper implements RowMapper<Menu> {
		
		@Override
		public Menu mapRow(ResultSet rs, int rowNum) throws SQLException {
			Menu menu = new Menu();
			menu.setMenuId(rs.getLong("menu_id"));
			menu.setParentMenuId(rs.getLong("parent_menu_id"));
			menu.setText(rs.getString("text"));
			menu.setDisplayOrder(rs.getInt("display_order"));
			menu.setItemId(rs.getString("item_id"));
			menu.setIconCss(rs.getString("icon_css"));
			menu.setPrivilegeNeeded(rs.getLong("privilege_needed"));
			menu.setLink(rs.getString("link"));
			return menu;
		}
		
	}

}
