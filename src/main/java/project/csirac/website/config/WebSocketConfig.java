package project.csirac.website.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 * Created by Dy.Zhao on 2016/1/3 0003.
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/emulator_response");
        config.setApplicationDestinationPrefixes("/emulator_in");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/emulator_in/handshake");
        registry.addEndpoint("/emulator_in/io");
        registry.addEndpoint("/emulator_in/control");
        registry.addEndpoint("/emulator_in/settings");

        registry.addEndpoint("/emulator_in/monitor");
        registry.addEndpoint("/emulator_in/debugger");
    }

}
