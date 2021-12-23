package com.city.message.repository;

import com.city.message.entity.ConversationsByUserEntity;
import com.city.message.entity.MessagesByConversationEntity;
import com.city.message.entity.UnreadMessagesCounterEntity;
import com.datastax.driver.core.querybuilder.Batch;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.datastax.oss.driver.api.core.cql.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraBatchOperations;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.query.Query;
import org.springframework.data.cassandra.core.query.Update;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static org.springframework.data.cassandra.core.query.Criteria.where;
import static org.springframework.data.cassandra.core.query.Query.query;

@Repository
public class MessageRepository {

    @Autowired
    private CassandraOperations template;


    public List<MessagesByConversationEntity> getMessages(UUID conversationId){
        Query query = query(where("conversation_id").is(conversationId));
        return template.select(query,MessagesByConversationEntity.class);
    }


    public MessagesByConversationEntity insertMessage(MessagesByConversationEntity message){
        return template.insert(message);
    }

    public boolean deleteMessage(UUID messageId){
        return false;
    }
}
