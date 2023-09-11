//package com.example.meongnyangbook.config;
//
//import com.example.meongnyangbook.post.repository.elastic.ElasticsearchPostRepository;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.elasticsearch.client.ClientConfiguration;
//import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
//import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
//
//@Configuration
////@EnableElasticsearchRepositories(basePackages = "com.example.meongnyangbook.post.repository.elastic")
//@EnableElasticsearchRepositories(basePackageClasses = {ElasticsearchPostRepository.class})
//public class ElasticSearchConfig extends ElasticsearchConfiguration {
//
//  @Override
//  public ClientConfiguration clientConfiguration() {
//    return ClientConfiguration.builder()
//        .connectedTo("localhost:9200")
//        .build();
//  }
//}