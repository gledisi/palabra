package com.city.message.entity;

import com.city.message.service.dto.UserDetails;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Table("conversations_by_user")
public class ConversationsByUserEntity implements Serializable {
    private static final long serialVersionUID = -3070309052393096283L;

    @PrimaryKey("user_id")
    private UUID userId;

    @Column("contact_name")
    private String contactName;

    @Column("contact_id")
    private UUID contactId;

    @Column("contact_photo")
    @CassandraType(type = CassandraType.Name.BLOB)
    private Byte[] contactPhoto;

    @Column("conversation_id")
    private UUID conversationId;

    @Column("last_msg_time")
    private LocalDateTime lastMsgTime;

    @Column("last_msg")
    private String lastMsg;

    @Column("unread_msg_count")
    private Integer unreadMsgCount;

    public void setContact(UserDetails userDetails){
        this.setContactId(userDetails.getUserUUID());
        this.setContactName(userDetails.getContactName());
        this.setContactPhoto(userDetails.getContactPhoto());
    }

    public void incrementUnreadMessagesCount() {
        this.unreadMsgCount+=unreadMsgCount;
    }
}
