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
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

/**
 * @ClassName: StartUp
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author huchao
 * @date 2019年11月26日
 *
 */
@SpringBootApplication
@EnableEurekaClient
@EnableScheduling
@EnableDiscoveryClient
@EnableHystrix
public class StartUp {

    public static void main(String[] args) {

        SpringApplication.run(StartUp.class, args);
    }

    @Bean
    public WebFilter corsFilter() {
        return (ServerWebExchange ctx, WebFilterChain chain) -> {
            ServerHttpRequest request = ctx.getRequest();
            if (!CorsUtils.isCorsRequest(request)) {
                return chain.filter(ctx);
            }

            HttpHeaders requestHeaders = request.getHeaders();
            ServerHttpResponse response = ctx.getResponse();
            HttpMethod requestMethod = requestHeaders.getAccessControlRequestMethod();
            HttpHeaders headers = response.getHeaders();
            headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, requestHeaders.getOrigin());
            headers.addAll(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, requestHeaders.getAccessControlRequestHeaders());
            if (requestMethod != null) {
                headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, requestMethod.name());
            }
            headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
            headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "all");
            headers.add(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "3600");
            if (request.getMethod() == HttpMethod.OPTIONS) {
                response.setStatusCode(HttpStatus.OK);
                return Mono.empty();
            }
            return chain.filter(ctx);
        };
    }

}
