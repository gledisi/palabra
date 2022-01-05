package com.city.message.repository;

import com.city.message.entity.UserActiveStatusEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.data.cassandra.core.query.Criteria.where;
import static org.springframework.data.cassandra.core.query.Query.query;

@Repository
public class UserActiveStatusRepository {

    @Autowired
    private CassandraOperations template;

    public UserActiveStatusEntity getStatus(UUID userId) {
        Query query = query(where("user_id").is(userId));
        return template.selectOne(query, UserActiveStatusEntity.class);
    }

    public UserActiveStatusEntity active(UUID userId) {
        UserActiveStatusEntity entity = new UserActiveStatusEntity();
        entity.setUserId(userId);
        entity.setIsActive(Boolean.TRUE);
        entity.setLastSeen(null);
        return template.insert(entity);
    }

    public UserActiveStatusEntity unActive(UUID userId) {
        UserActiveStatusEntity entity = new UserActiveStatusEntity();
        entity.setUserId(userId);
        entity.setIsActive(Boolean.FALSE);
        entity.setLastSeen(LocalDateTime.now());
        return template.insert(entity);
    }
}
