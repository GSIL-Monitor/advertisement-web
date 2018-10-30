package com.yuanshanbao.dsp.message.service;

import com.yuanshanbao.dsp.merchant.model.Merchant;
import com.yuanshanbao.dsp.message.dao.MessageDao;
import com.yuanshanbao.dsp.message.model.Message;
import com.yuanshanbao.paginator.domain.PageBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/10/26.
 */
@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageDao messageDao;

    @Override
    public List<Message> selectMessages(Message message, PageBounds pageBounds) {
        return messageDao.selectMessages(message,pageBounds);
    }

    @Override
    public int insertMessage(Message message) {
        return 0;
    }

    @Override
    public int deleteMessage(Long messageId) {
        return 0;
    }

    @Override
    public int updateMessage(Message message) {
        return 0;
    }
}
