package com.city.message.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Table("unread_messages_by_conversation")
public class UnreadMessagesByConversationEntity implements Serializable {
    private static final long serialVersionUID = -8612341285175653773L;

    @PrimaryKey("conversation_id")
    private UUID conversationId;

    @Column("message_id")
    private UUID messageId;

    @Column("from_user")
    private UUID fromUser;

    @Column("delivered_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime deliveredTime;

    @Column("text")
    private String text;

    @Column("url")
    private String url;

}