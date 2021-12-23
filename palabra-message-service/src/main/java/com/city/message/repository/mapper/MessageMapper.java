package com.city.message.repository.mapper;

import com.city.message.entity.MessagesByConversationEntity;
import com.datastax.oss.driver.api.core.cql.Row;
import org.springframework.data.cassandra.core.cql.RowMapper;

import java.time.LocalDateTime;

enum MessageMapper implements RowMapper<MessagesByConversationEntity> {

        INSTANCE;
        public MessagesByConversationEntity mapRow(Row row, int rowNum) {
            MessagesByConversationEntity message = new MessagesByConversationEntity();
            message.setMessageId(row.getUuid("message_id"));
            message.setFromUser(row.getUuid("from_user"));
            message.setConversationId(row.getUuid("conversation_id"));
            message.setDeliveredTime(LocalDateTime.from(row.getLocalDate("delivered_time")));
            message.setReadTime(LocalDateTime.from(row.getLocalDate("read_time")));
            message.setText(row.getString("text"));
            return message;
        }
    }
