package com.city.message.entity;

import lombok.Data;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.util.UUID;

@Data
@Table("user_conversation")
public class UserConversationEntity implements Serializable {
    private static final long serialVersionUID = -8933333931574132890L;

    @PrimaryKey
    private UserConversationKey key;

    @Column("conversation_id")
    private UUID conversationId;

    @Column("decryption_key")
    private String decryptionKey;
}
