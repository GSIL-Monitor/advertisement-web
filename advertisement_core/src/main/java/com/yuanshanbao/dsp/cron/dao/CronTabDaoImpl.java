package com.yuanshanbao.dsp.cron.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.dsp.base.dao.BaseDaoImpl;
import com.yuanshanbao.dsp.cron.model.CronTab;

@Repository
public class CronTabDaoImpl extends BaseDaoImpl implements CronTabDao {
    @Override
    public List<CronTab> getAllCronTab() {
        return getSqlSession().selectList("CronTab.getAllCronTab");
    }
}
