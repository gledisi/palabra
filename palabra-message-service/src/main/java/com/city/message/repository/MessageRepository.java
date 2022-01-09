package com.city.message.repository;

import com.city.message.entity.MessagesByConversationEntity;
import com.city.message.entity.UnreadMessagesByConversationEntity;
import com.city.message.entity.UnreadMessagesCounterEntity;
import com.city.message.service.mapper.ConversationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraBatchOperations;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.query.Query;
import org.springframework.data.cassandra.core.query.Update;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.springframework.data.cassandra.core.query.Criteria.where;
import static org.springframework.data.cassandra.core.query.Query.query;

@Repository
public class MessageRepository {

    @Autowired
    private CassandraOperations template;


    public List<MessagesByConversationEntity> getMessages(UUID conversationId) {
        Query query = query(where("conversation_id").is(conversationId));
        List<MessagesByConversationEntity> messages= template.select(query, MessagesByConversationEntity.class);
        messages.addAll(ConversationMapper.toReadMessage(getUnreadMessages(conversationId),null));
        return messages;
    }

    public List<UnreadMessagesByConversationEntity> getUnreadMessages(UUID conversationId) {
        Query query = query(where("conversation_id").is(conversationId));
        return template.select(query, UnreadMessagesByConversationEntity.class);
    }


    public MessagesByConversationEntity insertMessage(MessagesByConversationEntity message) {
        return template.insert(message);
    }

    public UnreadMessagesByConversationEntity insertUnreadMessage(UnreadMessagesByConversationEntity message) {
        return template.insert(message);
    }

    public boolean deleteMessage(UUID messageId) {
        return false;
    }

    public boolean updateReadTime(UUID cid) {
        String query = "UPDATE messages_by_conversation SET read_time=? Where conversation_id=? IF read_time=NULL ";
        return template.getCqlOperations().execute(query, LocalDateTime.now(),cid);
    }

    public boolean insertReadMessages(UUID uuid) {
        List<UnreadMessagesByConversationEntity> unreadMsg = getUnreadMessages(uuid);
        CassandraBatchOperations batchOperations = template.batchOps();
        batchOperations.insert(ConversationMapper.toReadMessage(unreadMsg,LocalDateTime.now()));
        batchOperations.execute();
        return true;
    }

    public boolean deleteUnreadMessaged(UUID cid) {
        String query = "Delete FROM unread_messages_by_conversation Where conversation_id=?";
        return template.getCqlOperations().execute(query,cid);
    }
}
