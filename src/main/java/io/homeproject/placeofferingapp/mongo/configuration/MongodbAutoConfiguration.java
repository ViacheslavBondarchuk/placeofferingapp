//package io.homeproject.placeofferingapp.mongo.configuration;
//
//import io.homeproject.placeofferingapp.mongo.converter.AuthorityConverter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
//
//import java.util.List;
//
///**
// * author: vbondarchuk
// * date: 11/16/2024
// * time: 5:29 PM
// **/
//
//@Configuration
//public class MongodbAutoConfiguration {
//
//    @Bean
//    public MongoCustomConversions customConversions() {
//        return new MongoCustomConversions(List.of(new AuthorityConverter()));
//    }
//}
