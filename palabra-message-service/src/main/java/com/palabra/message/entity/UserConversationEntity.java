package com.palabra.message.entity;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;

@Data
@Table("user_conversation")
public class UserConversationEntity implements Serializable {
    private static final long serialVersionUID = -8933333931574132890L;

    @PrimaryKey
    private Long id;

    @Column("user1_id")
    private Long user1Id;

    @Column("user2_id")
    private Long user2Id;

    @Column("decryption_key")
    private String decryptionKey;
}
