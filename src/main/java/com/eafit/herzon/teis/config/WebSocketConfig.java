package com.eafit.herzon.teis.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * The WebSocketConfig class configures the WebSocket server for live updates 
 * and routing of messages.
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  /**
   * Configures the message broker for our WebSocket server
   * including the prefixes for messages sent to and from clients.

   * @param config The message broker configuration.
   */
  @Override
  public void configureMessageBroker(MessageBrokerRegistry config) {
    config.enableSimpleBroker("/ws/topic"); // Prefix for messages sent to clients
    config.setApplicationDestinationPrefixes(
        "/ws/message"); // Prefix for messages sent from clients
  }

  /**
   * Registers the WebSocket endpoint for clients to connect to the server.

   * @param registry The Stomp endpoint registry.
   */
  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/ws/auction/websocket").withSockJS(); // WebSocket endpoint
  }
}