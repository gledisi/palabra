package com.city.message.repository;

import com.city.message.entity.MessagesByConversationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.query.Query;
import org.springframework.stereotype.Repository;

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
        return template.select(query, MessagesByConversationEntity.class);
    }


    public MessagesByConversationEntity insertMessage(MessagesByConversationEntity message) {
        return template.insert(message);
    }

    public boolean deleteMessage(UUID messageId) {
        return false;
    }
}
