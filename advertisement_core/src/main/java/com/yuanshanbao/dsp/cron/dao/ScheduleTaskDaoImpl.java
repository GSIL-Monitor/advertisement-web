package com.yuanshanbao.dsp.cron.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.dsp.base.dao.BaseDaoImpl;
import com.yuanshanbao.dsp.cron.model.ScheduleTask;

@Repository
public class ScheduleTaskDaoImpl extends BaseDaoImpl implements ScheduleTaskDao {
    @Override
    public ScheduleTask insert(ScheduleTask task) {
        int result = getSqlSession().insert("ScheduleTask.insert", task);
        if (result > 0) {
            return task;
        }
        return null;
    }

    @Override
    public void insert(List<ScheduleTask> taskList) {
        Map<String, Object> params = new HashMap<>();
        params.put("taskList", taskList);
        getSqlSession().insert("ScheduleTask.insertTaskList", params);
    }

    @Override
    public ScheduleTask getTaskById(Long taskId, Boolean isLock) {
        Map<String, Object> params = new HashMap<>();
        params.put("taskId", taskId);
        params.put("isLock", isLock);
        return getSqlSession().selectOne("ScheduleTask.getTaskById", params);
    }

    @Override
    public int updateTaskStatus(Long taskId, int newStatus, int oldStatus) {
        Map<String, Object> params = new HashMap<>();
        params.put("taskId", taskId);
        params.put("newStatus", newStatus);
        params.put("oldStatus", oldStatus);
        return getSqlSession().update("ScheduleTask.updateTaskStatus", params);
    }

    @Override
    public List<ScheduleTask> getUnfinishedTasks(Long taskId){
        Map<String, Object> params = new HashMap<>();
        params.put("taskId", taskId);
        return getSqlSession().selectList("ScheduleTask.getUnfinishedTasks", params);
    }
}
