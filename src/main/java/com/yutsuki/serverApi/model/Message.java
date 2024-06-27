package com.yutsuki.serverApi.model;

import com.yutsuki.serverApi.common.MessageStatus;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Message {
    private String senderName;
    private String receiverName;
    private String message;
    private String date;
    private MessageStatus status;
}
