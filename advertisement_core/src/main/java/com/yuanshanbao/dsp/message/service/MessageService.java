package com.yuanshanbao.dsp.message.service;

import com.yuanshanbao.dsp.merchant.model.Merchant;
import com.yuanshanbao.dsp.message.model.Message;
import com.yuanshanbao.paginator.domain.PageBounds;

import java.util.List;

/**
 * Created by Administrator on 2018/10/26.
 */
public interface MessageService {
    public List<Message> selectMessages(Message message, PageBounds pageBounds);

    public int insertMessage(Message message);

    public int deleteMessage(Long  messageId);

    public int updateMessage(Message message);
}
