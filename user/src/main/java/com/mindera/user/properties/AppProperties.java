package com.mindera.user.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "app.school")
@Getter
@Setter
public class AppProperties {
    private Redis redis;

    @Getter
    @Setter
    public static class Redis {
        private Integer ttlInMillis = 30000;
    }

}
