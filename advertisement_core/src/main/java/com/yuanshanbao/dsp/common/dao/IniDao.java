package com.yuanshanbao.dsp.common.dao;

import java.util.List;

import com.yuanshanbao.dsp.common.model.Ini;

public interface IniDao {
    List<Ini> getAllIni();

    Ini getIni(String iniName);

    void updateIni(Ini ini);

    void insert(Ini ini);

    List<Integer> monitorDB();
}
