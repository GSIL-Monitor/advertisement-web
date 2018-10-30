package com.yuanshanbao.dsp.message.dao;

import com.yuanshanbao.dsp.base.dao.BaseDaoImpl;
import com.yuanshanbao.dsp.merchant.model.Merchant;
import com.yuanshanbao.dsp.message.model.Message;
import com.yuanshanbao.paginator.domain.PageBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2018/10/26.
 */
@Repository
public class MessageDaoImpl extends BaseDaoImpl implements MessageDao{

    @Override
    public List<Message> selectMessages(Message message, PageBounds pageBounds) {
        return getSqlSession().selectList("message.selectMessage",message,pageBounds);
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
