package com.yuanshanbao.ad.common.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.ad.base.dao.BaseDaoImpl;
import com.yuanshanbao.ad.common.model.Ini;

@Repository
public class IniDaoImpl extends BaseDaoImpl implements IniDao {
    @Override
    public List<Ini> getAllIni() {
        return getSqlSession().selectList("Ini.getAllIni");
    }

    @Override
    public Ini getIni(String iniName) {
        return getSqlSession().selectOne("Ini.getIni", iniName);
    }

    @Override
    public void updateIni(Ini ini) {
        getSqlSession().update("Ini.update", ini);
    }

    @Override
    public void insert(Ini ini) {
        getSqlSession().insert("Ini.insert", ini);
    }

    @Override
    public List<Integer> monitorDB() {
        return getSqlSession().selectList("Ini.monitorDB");
    }
}
