package com.city.message.repository;

import com.city.message.entity.ConversationEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface ConversationRepository extends CassandraRepository<ConversationEntity,Long> {
}
