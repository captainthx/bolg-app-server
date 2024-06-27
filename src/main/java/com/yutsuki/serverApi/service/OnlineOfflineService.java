package com.yutsuki.serverApi.service;

import com.yutsuki.serverApi.jwt.UserDetailsImp;
import com.yutsuki.serverApi.model.response.AccountResponse;
import com.yutsuki.serverApi.repository.AccountRepository;
import com.yutsuki.serverApi.repository.ConversationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OnlineOfflineService {
    private final Set<Long> onlineUsers = new ConcurrentSkipListSet<>();
    private final Map<Long, Set<String>> subscribedUsers = new ConcurrentHashMap<>();

    @Resource
    private AccountRepository accountRepository;

    public void addOnlineUser(UserDetailsImp userDetailsImp) {
        if (Objects.nonNull(userDetailsImp)) {
            log.info("{} is online", userDetailsImp.getUsername());
            onlineUsers.add(userDetailsImp.getId());
        }
    }

    public void addUserSubscription(UserDetailsImp userDetails, String subscriptionChannel) {
        if (Objects.nonNull(userDetails)) {
            log.info("{} is subscribed {}", userDetails.getUsername(), subscriptionChannel);
            Set<String> subscriptions = subscribedUsers.getOrDefault(userDetails.getId(), new HashSet<>());
            subscriptions.add(subscriptionChannel);
            subscribedUsers.put(userDetails.getId(), subscriptions);
        }
    }

    public void removeSubscription(UserDetailsImp userDetails, String subscriptionChannel) {
        log.info("{} is unsubscribed {} onlineUser:{}", userDetails.getUsername(), subscriptionChannel, onlineUsers);
        Set<String> subscriptions = subscribedUsers.getOrDefault(userDetails.getId(), new HashSet<>());
        subscriptions.remove(subscriptionChannel);
        subscribedUsers.remove(userDetails.getId(), subscriptions);
        onlineUsers.remove(userDetails.getId());
        log.info(" subscribedUsers: {} online:{}", subscribedUsers,onlineUsers);
    }


    public boolean isUserOnline(Long userId) {
        return onlineUsers.contains(userId);
    }

    public boolean isUserSubscribed(Long userId, String subscriptionChannel) {
        Set<String> subscriptions = subscribedUsers.getOrDefault(userId, new HashSet<>());
        return subscriptions.contains(subscriptionChannel);
    }

    public List<AccountResponse> getListUserOnline() {
        return accountRepository.findAllById(onlineUsers).stream().map(AccountResponse::build).collect(Collectors.toList());
    }

}




