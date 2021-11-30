package com.city.message.repository;

import com.city.message.entity.ConversationMessageEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.List;

public interface ConversationMessageRepository extends CassandraRepository<ConversationMessageEntity,Long> {
    List<ConversationMessageEntity> findByConversationId(Long userId);
}
