package com.yuanshanbao.dsp.cron.dao;

import java.util.List;

import com.yuanshanbao.dsp.cron.model.ScheduleTask;

public interface ScheduleTaskDao {
    ScheduleTask insert(ScheduleTask task);

    void insert(List<ScheduleTask> taskList);

    ScheduleTask getTaskById(Long taskId, Boolean isLock);

    int updateTaskStatus(Long taskId, int newStatus, int oldStatus);

    List<ScheduleTask> getUnfinishedTasks(Long taskId);
}
