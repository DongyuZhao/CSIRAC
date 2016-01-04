package project.csirac.config;

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
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer
{

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.enableSimpleBroker("/emulator_response");
        config.setApplicationDestinationPrefixes("/socket");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/hello").withSockJS();
        registry.addEndpoint("/emulator_in/keep_alive").withSockJS();
        registry.addEndpoint("/emulator_in/disconnect").withSockJS();
        registry.addEndpoint("/emulator_in/upload_program").withSockJS();
        registry.addEndpoint("/emulator_in/start").withSockJS();
        registry.addEndpoint("/emulator_in/pause").withSockJS();
        registry.addEndpoint("/emulator_in/stop").withSockJS();
    }

}
