package com.yuanshanbao.ms.service.admin.impl;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanshanbao.ms.mapper.admin.IconMapper;
import com.yuanshanbao.ms.model.admin.Icon;
import com.yuanshanbao.ms.service.admin.IconService;
import com.yuanshanbao.paginator.domain.PageBounds;

@Service
public class IconServiceImpl implements IconService {

	@Autowired
	private IconMapper iconMapper;
	
	@Resource(name="sysDataSource")
	private DataSource dataSource;
	
	@Override
	public Blob queryIconByName(String name) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = dataSource.getConnection();
			ps = con.prepareStatement("select * from tb_admin_icons where name=?");
			ps.setString(1, name);
			
			rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getBlob("image");
			}
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				
				if (ps != null) {
					ps.close();
				}
				
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	@Override
	public List<String> queryIconNames() {
		return iconMapper.queryIconNames();
	}

	@Override
	public List<Icon> queryIcons(String iconName, PageBounds pageBounds) {
		Icon icon = new Icon();
		icon.setName(iconName);
		return iconMapper.queryIcons(icon, pageBounds);
	}

	@Override
	public boolean insertIcon(String name, byte[] image) {
		int result = -1;
		
		result = iconMapper.insertIcon(name, image);
		
		if (result >= 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean deleteIcon(String name) {
		int result = -1;
		
		result = iconMapper.deleteIcon(name);
		
		if (result >= 0) {
			return true;
		} else {
			return false;
		}
	}
}
