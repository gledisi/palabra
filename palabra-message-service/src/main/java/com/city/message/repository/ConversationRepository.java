package com.city.message.repository;

import com.city.message.entity.ConversationsByUserEntity;
import com.city.message.entity.UserConversationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.query.Query;
import org.springframework.stereotype.Repository;

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


    public boolean deleteById(UUID fromString) {
        return false;
    }

    public ConversationsByUserEntity insert(ConversationsByUserEntity conversationsByUserEntity) {
        return template.insert(conversationsByUserEntity);
    }
}
