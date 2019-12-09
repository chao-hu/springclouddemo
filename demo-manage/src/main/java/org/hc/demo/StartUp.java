/**
 * @Title: StartUp.java
 * @Package org.hc.demo
 * @Description: TODO(用一句话描述该文件做什么)
 * @author huchao
 * @date 2019年11月26日
 * @version V1.0
 */
package org.hc.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName: StartUp
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author huchao
 * @date 2019年11月26日
 *
 */
@SpringBootApplication
@EnableEurekaClient
public class StartUp {

    public static void main(String[] args) {

        SpringApplication.run(StartUp.class, args);
    }

    @Bean
    Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return new Jackson2ObjectMapperBuilderCustomizer() {
            @Override
            public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
                jacksonObjectMapperBuilder.failOnEmptyBeans(false).failOnUnknownProperties(false);
            }
        };
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
