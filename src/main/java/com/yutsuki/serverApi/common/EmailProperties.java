package com.yutsuki.serverApi.common;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Getter
@Setter
public class EmailProperties {
    @Value("${email.reset-password-form}")
    private String resetPasswordForm;
    @Value("${email.reset-password-url}")
    private String resetPasswordUrl;
}
