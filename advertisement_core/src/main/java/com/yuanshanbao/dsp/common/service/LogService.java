package com.yuanshanbao.dsp.common.service;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

/**
 * @author singal
 */

public interface LogService {
    void monitor(Logger log, Level level, String key, String message);
}