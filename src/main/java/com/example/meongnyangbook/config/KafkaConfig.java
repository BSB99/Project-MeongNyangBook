//package com.example.meongnyangbook.config;
//
//import com.example.meongnyangbook.kafka.AlarmRequestDto;
//import com.example.meongnyangbook.kafka.AlarmRequestDtoDeserializer;
//import java.util.HashMap;
//import java.util.Map;
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.clients.producer.ProducerConfig;
//import org.apache.kafka.common.serialization.ByteArrayDeserializer;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.apache.kafka.common.serialization.StringSerializer;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.annotation.EnableKafka;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.core.ConsumerFactory;
//import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
//import org.springframework.kafka.core.DefaultKafkaProducerFactory;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.core.ProducerFactory;
//import org.springframework.kafka.support.serializer.JsonDeserializer;
//
//@Configuration
//@EnableKafka
//public class KafkaConfig {
//
//  @Value("${spring.kafka.bootstrap-servers}")
//  private String bootstrapServers;
//  @Value("${spring.kafka.consumer.group-id}")
//  private String consumerGroup;
//
//
//  @Bean
//  public ProducerFactory<String, byte[]> producerFactory() {
//    Map<String, Object> configProps = new HashMap<>();
//    configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
//    configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//    configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class);
//    return new DefaultKafkaProducerFactory<>(configProps);
//  }
//
//  @Bean
//  public ConsumerFactory<String, Json> consumerFactory() {
//    Map<String, Object> configProps = new HashMap<>();
//    configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
//    configProps.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroup);
//    configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//    configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
//        JsonDeserializer.class);
//    return new DefaultKafkaConsumerFactory<>(configProps);
//  }
//
//  @Bean
//  public KafkaTemplate<String, AlarmRequestDto> kafkaTemplate() {
//    return new KafkaTemplate<>(producerFactory());
//  }
//
//  @Bean
//  public ConcurrentKafkaListenerContainerFactory<String, AlarmRequestDto> kafkaListenerContainerFactory() {
//    ConcurrentKafkaListenerContainerFactory<String, AlarmRequestDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
//    factory.setConsumerFactory(consumerFactory());
//    return factory;
//  }
//}