/**
 * @Title: StartUp.java
 * @Package org.hc.demo
 * @Description: TODO(用一句话描述该文件做什么)
 * @author huchao
 * @date 2019年11月26日
 * @version V1.0
 */
package org.hc.demo;

import org.hc.demo.service.ISendeService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.stream.annotation.EnableBinding;

/**
 * @ClassName: StartUp
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author huchao
 * @date 2019年11月26日
 *
 */
@SpringBootApplication
@EnableEurekaClient
// 绑定我们刚刚创建的发送消息的接口类型
@EnableBinding(value = { ISendeService.class })
public class StartUp {

    public static void main(String[] args) {

        SpringApplication.run(StartUp.class, args);
    }
}
