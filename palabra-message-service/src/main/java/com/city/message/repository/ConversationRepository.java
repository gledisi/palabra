package com.city.message.repository;

import com.city.message.entity.ConversationsByUserEntity;
import com.city.message.entity.UnreadMessagesCounterEntity;
import com.city.message.entity.UserConversationEntity;
import com.city.message.entity.UserConversationKey;
import com.city.message.service.dto.NewTextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraBatchOperations;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.query.Query;
import org.springframework.data.cassandra.core.query.Update;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Arrays;
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
        ConversationsByUserEntity conv = findByUserIdAndConversationId(userId,convUuid);
        conv.setLastMsg(message.getText());
        conv.setLastMsgTime(LocalDateTime.now());
        return template.insert(conv);
    }

    public ConversationsByUserEntity updateConversationByUserAndCounter(NewTextMessage message,UUID userId) {
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

    public boolean decrementCounter(UUID cId, UUID uId, int msgCount) {
        Query query = query(where("conversation_id").is(cId)).and(where("user_id").is(uId));
        Update update = Update.empty().decrement("count",msgCount);
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

    public boolean insert(ConversationsByUserEntity from,ConversationsByUserEntity to) {
        List<ConversationsByUserEntity> convs = Arrays.asList(from,to);
        CassandraBatchOperations batch = template.batchOps();
        batch.insert(convs);
        insertUserConversation(from.getUserId(),to.getUserId(),to.getConversationId(),batch);
        updateUnreadCounter(to.getConversationId(),to.getUserId());
        batch.execute();
        return true;
    }

    public Boolean insertUserConversation(UUID user1Id,UUID user2Id,UUID convId,CassandraBatchOperations batch) {
        UserConversationEntity entity = new UserConversationEntity();
        entity.setKey(new UserConversationKey(user1Id,user2Id));
        entity.setConversationId(convId);
        entity.setDecryptionKey("ddd");
        batch.insert(entity);
        return true;
    }

//    public Boolean updateUnreadCounter(UUID conversationId,UUID userId) {
//        String query ="UPDATE unread_messages_counter SET count=count+1 WHERE conversation_id=? AND user_id=? ;";
//        return template.getCqlOperations().execute(query,conversationId,userId);
//    }

    public int resetUnreadMsgCounter(UUID uId, UUID cId) {
        int unreadMsg=selectUnreadMsgCount(uId,cId);
        Query query = query(where("user_id").is(uId)).and(where("conversation_id").is(cId));
        Update update = Update.update("unread_msg_count",0);
        template.update(query,update, ConversationsByUserEntity.class);
        return unreadMsg;
    }

    private int selectUnreadMsgCount(UUID uId, UUID cId){
        String query = "Select unread_msg_count FROM conversations_by_user Where user_id=? and conversation_id=?";
        return template.getCqlOperations().queryForObject(query,Integer.class,uId,cId);
    }
}
