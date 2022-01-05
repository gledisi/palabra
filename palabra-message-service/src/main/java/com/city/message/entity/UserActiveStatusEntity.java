package com.city.message.entity;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@Table("user_active_status")
public class UserActiveStatusEntity {

    @PrimaryKey("user_id")
    private UUID userId;

    @Column("is_active")
    private Boolean isActive;

    @Column("last_seen")
    private LocalDateTime lastSeen;

}
