package com.city.message.repository;

import com.city.message.entity.UserConversationEntity;
import com.city.message.entity.UserConversationKey;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface UserConversationRepository extends CassandraRepository<UserConversationEntity,UserConversationKey> {
}
