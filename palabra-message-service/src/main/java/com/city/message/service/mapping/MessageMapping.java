package com.city.message.service.mapping;

import com.city.message.entity.ConversationEntity;
import com.city.message.entity.UserConversationEntity;
import com.city.message.entity.UserConversationKey;
import com.city.message.service.dto.NewConversationMessage;
import com.city.message.service.dto.NewTextMessage;

public class MessageMapping {

    public static UserConversationEntity toEntity(NewConversationMessage textMessage){
        UserConversationEntity entity = new UserConversationEntity();
        entity.setUserConversationKey(new UserConversationKey(textMessage.getFromUser(),textMessage.getToUser()));
        return entity;
    }
    public static ConversationEntity toEntity(NewTextMessage newTextMessage){
        ConversationEntity entity = new ConversationEntity();
        entity.setFromUser(newTextMessage.getFromUser());
        entity.setText(newTextMessage.getText());
        return entity;
    }
}
