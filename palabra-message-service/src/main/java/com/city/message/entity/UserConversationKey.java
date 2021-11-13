package com.city.message.entity;

import lombok.Data;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.io.Serializable;

@Data
@PrimaryKeyClass
public class UserConversationKey implements Serializable {
    private static final long serialVersionUID = -6358179192224357288L;

    @PrimaryKeyColumn(name = "user1_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED, ordering = Ordering.DESCENDING)
    private Long user1Id;
    @PrimaryKeyColumn(name = "user2_id", ordinal = 1, type = PrimaryKeyType.PARTITIONED, ordering = Ordering.DESCENDING)
    private Long user2Id;

    public UserConversationKey(Long fromUser, Long toUser) {
        this.user1Id = fromUser;
        this.user2Id=toUser;
    }
}
