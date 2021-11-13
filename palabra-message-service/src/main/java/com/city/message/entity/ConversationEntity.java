package com.city.message.entity;

import lombok.Data;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Table("conversation")
public class ConversationEntity implements Serializable {
    private static final long serialVersionUID = -8185299273611795521L;
    @PrimaryKeyColumn(name = "id", ordinal = 0, type = PrimaryKeyType.PARTITIONED, ordering = Ordering.DESCENDING)
    private Long id;

    @Column("delivered_time")
    private LocalDateTime deliveredTime;
    @Column("text")
    private String text;
    @Column("url")
    private String url;
    @Column("from_user")
    private Long fromUser;

}
