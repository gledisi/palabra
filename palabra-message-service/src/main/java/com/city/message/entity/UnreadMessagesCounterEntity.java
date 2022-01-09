package com.city.message.entity;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Data
@Table("unread_messages_counter")
public class UnreadMessagesCounterEntity {

    @PrimaryKey("conversation_id")
    private UUID conversationId;

    @Column("user_id")
    private UUID userId;

    @Column("count")
    @CassandraType(type = CassandraType.Name.COUNTER)
    private Integer count;
}
