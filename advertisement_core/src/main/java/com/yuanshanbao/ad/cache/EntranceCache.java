package com.yuanshanbao.ad.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.ad.common.dao.EntranceDao;
import com.yuanshanbao.ad.common.model.Entrance;

/**
 * Created by Ynght on 2016/12/9.
 */
public class EntranceCache {

    public static Map<Integer, List<Entrance>> entranceMap = new HashMap<>();

    @Autowired
    private EntranceDao entranceDao;

    private EntranceCache() {
    }

    public void init() {
        LoggerUtil.info("init EntranceCache");
        refresh();
    }

    public void refresh() {
        List<Entrance> entrances = entranceDao.getAllEntrances();
        Map<Integer, List<Entrance>> temp = new HashMap<>();
        for (Entrance e : entrances) {
            List<Entrance> temps = temp.get(e.getPositionType());
            if (temps == null) {
                temps = new ArrayList<>();
                temp.put(e.getPositionType(), temps);
            }
            temps.add(e);
        }
        entranceMap = temp;
        LoggerUtil.info("refresh " + (entrances == null ? 0 : entrances.size()) + " entrance");
    }

    public static List<Entrance> getEntrancesByType(Integer positionType) {
        return entranceMap.get(positionType);
    }

    public static Map<Integer, List<Entrance>> getEntrancesMap() {
        return entranceMap;
    }
}
