/**
 * @Title: ProductController.java
 * @Package org.hc.demo.controller
 * @Description: TODO(用一句话描述该文件做什么)
 * @author huchao
 * @date 2019年11月26日
 * @version V1.0
 */
package org.hc.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hc.demo.FlagConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName: ProductController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author huchao
 * @date 2019年11月26日
 *
 */
@RestController
@RefreshScope // 刷新配置
public class ProductController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    DiscoveryClient client;

    @Autowired
    FlagConfig flagConfig;

    @RequestMapping("/hi")
    public Object hi(String name) {

        String url = "http://demo-provider/provider/hi?name=" + name;
        String res = restTemplate.getForObject(url, String.class);

        return res;
    }

    @RequestMapping("/discover")
    public Object discover() {

        StringBuilder buf = new StringBuilder();
        List<String> serviceIds = client.getServices();
        if (!CollectionUtils.isEmpty(serviceIds)) {
            for (String s : serviceIds) {
                System.out.println("serviceId:" + s);
                List<ServiceInstance> serviceInstances = client.getInstances(s);
                if (!CollectionUtils.isEmpty(serviceInstances)) {
                    for (ServiceInstance si : serviceInstances) {
                        buf.append("[" + si.getServiceId() + " host=" + si.getHost() + " port=" + si.getPort() + " uri="
                                + si.getUri() + "]");
                    }
                } else {
                    buf.append("no service.");
                }
            }
        }

        return buf.toString();
    }

    @RequestMapping("/conf")
    public Object conf() {

        if (flagConfig == null) {

            return "空";
        }

        FlagConfig conf = new FlagConfig();
        conf.setA(flagConfig.getA());
        conf.setB(flagConfig.getB());
        conf.setC(flagConfig.getC());
        conf.setD(flagConfig.getD());
        conf.setE(flagConfig.getE());

        return conf;
    }

    @Value("${flag-a}")
    private String a;
    @Value("${flag-b}")
    private String b;
    @Value("${flag-c}")
    private String c;
    @Value("${flag-d}")
    private String d;
    @Value("${flag-e}")
    private String e;

    @RequestMapping("/flag")
    public Object flag() {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("flag.a", a);
        map.put("flag.b", b);
        map.put("flag.c", c);
        map.put("flag.d", d);
        map.put("flag.e", e);

        return map;
    }

}
