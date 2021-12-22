package com.city.message.repository;

import com.city.message.entity.MessagesByConversationEntity;
import com.city.message.service.dto.ConversationMessages;
import com.city.message.service.dto.ForwardMessage;
import com.city.message.service.dto.NewTextMessage;
import com.city.message.service.dto.ReplyMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
