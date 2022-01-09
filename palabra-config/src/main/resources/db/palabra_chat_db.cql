DROP KEYSPACE IF EXISTS palabra_chat;

CREATE KEYSPACE palabra_chat WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1'};

USE palabra_chat;

CREATE TABLE conversations_by_user (
          user_id uuid,
          contact_name varchar,
          contact_id uuid,
          contact_photo BLOB,
          conversation_id uuid,
          last_msg_time timestamp,
          last_msg text,
          unread_msg_count int,   
          PRIMARY KEY ((user_id), conversation_id))
          WITH CLUSTERING ORDER BY (conversation_id DESC);
          
CREATE TABLE messages_by_conversation (
          conversation_id uuid,
          message_id uuid,
          delivered_time timestamp,
          read_time timestamp,
          text text,
          url varchar,
          from_user uuid,
          PRIMARY KEY ((conversation_id), delivered_time, message_id))
          WITH CLUSTERING ORDER BY (delivered_time ASC );

CREATE TABLE unread_messages_by_conversation (
          conversation_id uuid,
          message_id uuid,
          delivered_time timestamp,
          text text,
          url varchar,
          from_user uuid,
          PRIMARY KEY ((conversation_id), delivered_time, message_id))
          WITH CLUSTERING ORDER BY (delivered_time ASC );

CREATE TABLE unread_messages_counter (
          conversation_id uuid,
          user_id uuid,
          count COUNTER,
          PRIMARY KEY ((conversation_id,user_id)));  

CREATE TABLE user_conversation (
          user1_id uuid,
          user2_id uuid,
          conversation_id uuid,
          decryption_key varchar,
          PRIMARY KEY ((user1_id , user2_id))
          );

CREATE TABLE user_active_status (
          user_id uuid  PRIMARY KEY,
          is_active boolean,
          last_seen timestamp
    );