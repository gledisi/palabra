package com.palabra.message.repository;

import com.palabra.message.entity.UserConversationEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.List;

public interface UserConversationRepository extends CassandraRepository<UserConversationEntity,Long> {

    public List<UserConversationEntity> findByUser1IdOrUser2Id(Long user1Id, Long user2Id);
}
