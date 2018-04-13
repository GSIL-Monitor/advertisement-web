package com.yuanshanbao.dsp.common.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.dsp.base.dao.BaseDaoImpl;
import com.yuanshanbao.dsp.common.model.Entrance;
import com.yuanshanbao.paginator.domain.PageBounds;

/**
 * Created by Ynght on 2016/12/9.
 */
@Repository
public class EntranceDaoImpl extends BaseDaoImpl implements EntranceDao {

    @Override
    public List<Entrance> getAllEntrances() {
        return getSqlSession().selectList("Entrance.getAllEntrances");
    }

    @Override
    public int insert(Entrance entrance) {
        return getSqlSession().insert("Entrance.insert", entrance);
    }

    @Override
    public int update(Entrance entrance) {
        return getSqlSession().update("Entrance.update", entrance);
    }

	@Override
	public List<Entrance> selectEntrances(Entrance entrance, PageBounds pageBounds) {
		return getSqlSession().selectList("Entrance.selectEntrances", entrance, pageBounds);
	}
}
