package com.yutsuki.serverApi.repository;

import com.yutsuki.serverApi.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    long countById(Long id);
}