package com.yuanshanbao.ad.user.service;

import java.util.List;

import com.yuanshanbao.ad.user.model.IndexUser;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface IndexUserService {

	public List<IndexUser> selectIndexUsers(IndexUser indexUser, PageBounds pageBounds);

	public IndexUser selectIndexUser(String openId);

	public void insertIndexUser(IndexUser indexUser);

}
