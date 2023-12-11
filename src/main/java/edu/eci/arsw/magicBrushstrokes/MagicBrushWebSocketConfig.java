package edu.eci.arsw.magicBrushstrokes;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;


@Configuration
@EnableWebSocketMessageBroker
public class MagicBrushWebSocketConfig implements WebSocketMessageBrokerConfigurer{
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/game");
        config.setApplicationDestinationPrefixes("/app");        
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //registry.addEndpoint("/stompendpoint").setAllowedOrigins("https://magicbrushstrokes.azurewebsites.net", "http://magicbrushstrokes.azurewebsites.net", "http://magicbalancer.eastus2.cloudapp.azure.com").withSockJS(); // Cambiar al momento de subir a azure
        registry.addEndpoint("/stompendpoint").setAllowedOrigins("http://192.168.1.11:3000").withSockJS();
    }
}
