package com.city.message.repository;

import com.city.message.entity.ConversationsByUserEntity;
import com.city.message.entity.UnreadMessagesCounterEntity;
import com.city.message.entity.UserConversationEntity;
import com.city.message.service.dto.NewTextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.query.Query;
import org.springframework.data.cassandra.core.query.Update;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.springframework.data.cassandra.core.query.Criteria.where;
import static org.springframework.data.cassandra.core.query.Query.query;

@Repository
public class ConversationRepository {

    @Autowired
    private CassandraOperations template;

    public ConversationsByUserEntity findByUserIdAndConversationId(UUID uId, UUID cId) {
        Query query = query(where("user_id").is(uId))
                        .and(where("conversation_id").is(cId));
        return template.selectOne(query, ConversationsByUserEntity.class);
    }

    public List<ConversationsByUserEntity> findByUserId(UUID uId) {
        Query query = query(where("user_id").is(uId));
        return template.select(query, ConversationsByUserEntity.class);
    }

    public UserConversationEntity findUserConversation(UUID u1Id,UUID u2Id) {
        Query query = query(where("user1_id").in(u1Id,u2Id)).and(where("user2_id").in(u2Id,u1Id));
        return template.selectOne(query, UserConversationEntity.class);
    }

    public ConversationsByUserEntity updateConversationByUser(NewTextMessage message,UUID userId) {
        UUID convUuid = message.getConversationId();
        UnreadMessagesCounterEntity unreadMsgCount = getUnreadCounter(convUuid,userId);
        ConversationsByUserEntity conv = findByUserIdAndConversationId(userId,convUuid);
        conv.setLastMsg(message.getText());
        conv.setLastMsgTime(LocalDateTime.now());
        conv.setUnreadMsgCount(unreadMsgCount.getCount());
        return template.insert(conv);
    }


    public Boolean updateUnreadCounter(UUID conversationId,UUID userId) {
        Query query = query(where("conversation_id").is(conversationId)).and(where("user_id").is(userId));
        Update update = Update.empty().increment("count");
        return template.update(query,update, UnreadMessagesCounterEntity.class);
    }

    public UnreadMessagesCounterEntity getUnreadCounter(UUID conversationId,UUID userId) {
        Query query = query(where("conversation_id").is(conversationId)).and(where("user_id").is(userId));
        return template.selectOne(query, UnreadMessagesCounterEntity.class);
    }

    public boolean deleteById(UUID cId) {
        Query query = query(where("conversation_id").is(cId));
        return template.delete(query,ConversationsByUserEntity.class);
    }

    public ConversationsByUserEntity insert(ConversationsByUserEntity conversationsByUserEntity) {
        return template.insert(conversationsByUserEntity);
    }
}
