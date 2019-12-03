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
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @ClassName: StartUp
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author huchao
 * @date 2019年11月26日
 *
 */
@SpringBootApplication
@EnableEurekaServer
public class StartUp {

    /**
     * TODO 简单描述
     * @Title: main
     * @Description: TODO详细描述
     * @param args void
     * @throws
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        SpringApplication.run(StartUp.class, args);
    }

}
