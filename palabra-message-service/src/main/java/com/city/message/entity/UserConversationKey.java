package com.city.message.entity;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@PrimaryKeyClass
public class UserConversationKey implements Serializable {
    private static final long serialVersionUID = -7962596704773348562L;

    @PrimaryKeyColumn(name="user1_id" , ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private UUID user1Id;

    @PrimaryKeyColumn(name="user2_id", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    private UUID user2Id;

    public UserConversationKey(UUID user1Id, UUID user2Id) {
        this.user1Id = user1Id;
        this.user2Id = user2Id;
    }

    public UserConversationKey() {
    }

    public UUID getUser1Id() {
        return user1Id;
    }

    public void setUser1Id(UUID user1Id) {
        this.user1Id = user1Id;
    }

    public UUID getUser2Id() {
        return user2Id;
    }

    public void setUser2Id(UUID user2Id) {
        this.user2Id = user2Id;
    }

    @Override
    public String toString() {
        return "UserConversationKey{" +
                "user1Id=" + user1Id +
                ", user2Id=" + user2Id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserConversationKey)) return false;
        UserConversationKey that = (UserConversationKey) o;
        return getUser1Id().equals(that.getUser1Id()) && getUser2Id().equals(that.getUser2Id());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUser1Id(), getUser2Id());
    }
}
