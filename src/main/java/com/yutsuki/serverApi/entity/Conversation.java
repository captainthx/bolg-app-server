package com.yutsuki.serverApi.entity;

import com.yutsuki.serverApi.common.MessageStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "conversation")
public class Conversation extends BaseEntity {
    @ManyToOne
    private Account formUser;
    @ManyToOne
    private Account toUser;
    private String message;
    @Enumerated(EnumType.STRING)
    private MessageStatus status;

}