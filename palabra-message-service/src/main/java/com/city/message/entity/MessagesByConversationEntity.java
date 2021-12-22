package com.city.message.entity;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Table("messages_by_conversation")
public class MessagesByConversationEntity implements Serializable {
    private static final long serialVersionUID = -4628383980504875248L;

    @PrimaryKey("conversation_id")
    @CassandraType(type = CassandraType.Name.TIMEUUID)
    private UUID conversationId;

    @Column("message_id")
    private UUID messageId;

    @Column("from_user")
    private UUID fromUser;

    @Column("delivered_time")
    private LocalDateTime deliveredTime;

    @Column("read_time")
    private LocalDateTime readTime;

    @Column("text")
    private String text;

    @Column("url")
    private String url;

}
