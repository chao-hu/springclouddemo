/**
 * @Title: ProductController.java
 * @Package org.hc.demo.controller
 * @Description: TODO(用一句话描述该文件做什么)
 * @author huchao
 * @date 2019年11月26日
 * @version V1.0
 */
package org.hc.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: ProductController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author huchao
 * @date 2019年11月26日
 *
 */
@RestController
public class ProductController {

    @RequestMapping("/hi")
    public Object hi(String name) {

        return "Hello " + name;
    }

    @RequestMapping("/timeout")
    public String timeout() {
        try {
            // 睡5秒，网关Hystrix3秒超时
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "timeout";
    }
}
