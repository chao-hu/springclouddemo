/**
 * @Title: ProductController.java
 * @Package org.hc.demo.controller
 * @Description: TODO(用一句话描述该文件做什么)
 * @author huchao
 * @date 2019年11月26日
 * @version V1.0
 */
package org.hc.demo.controller;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.hc.demo.service.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

/**
 * @ClassName: HelloController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author zyh
 * @date 2019年12月10日
 *
 */
@RestController
public class HelloController {

    @Autowired
    private HelloService helloService;

    @GetMapping("/hi")
    public String hi(String name) {
        return helloService.hiService(name);
    }

    @GetMapping("/hey")
    public String hey() {
        return helloService.heyService();
    }

    @GetMapping("/oh")
    public String oh() {
        return helloService.ohService();
    }

    @GetMapping("/ah")
    public String ah() {
        return helloService.ahService();
    }

    private static final Logger LOG = LoggerFactory.getLogger(ProductController.class);

    @RequestMapping(value = "/ok", method = RequestMethod.GET)
    @HystrixCommand(fallbackMethod = "okFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "100"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "50")

    })
    public String ok() throws Exception {
        int l = new Random().nextInt(200);
        LOG.info(String.format("l=%s", l));
        TimeUnit.MILLISECONDS.sleep(l);
        return "ok" + l;
    }

    public String okFallback(Throwable e) {
        System.out.println("execute okFallback!" + e.getMessage());
        LOG.error("error", e);
        return "fallback";
    }

    public String okFallback() {
        return "fallbackssssss";
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String ok1() throws Exception {
        int l = new Random().nextInt(3000);
        LOG.info(String.format("l=%s", l));
        TimeUnit.MILLISECONDS.sleep(l);
        return "ok" + l;
    }

    @RequestMapping(value = "/test1", method = RequestMethod.GET)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public void s400() {
        LOG.info("Bad request");
    }

    @RequestMapping(value = "/test2", method = RequestMethod.GET)
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    public void s403() {
        LOG.info("FORBIDDEN request");
    }

    @RequestMapping(value = "/test3", method = RequestMethod.GET)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public void s500() {
        LOG.info("INTERNAL_SERVER_ERROR ");
    }

    @RequestMapping(value = "/test5", method = RequestMethod.GET)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public void s5005() {
        LOG.info("BAD_REQUEST ");
    }

    @RequestMapping(value = "/test6", method = RequestMethod.GET)
    @ResponseStatus(code = HttpStatus.GONE)
    public void s50056() {
        LOG.info("GONE ");
    }

    @RequestMapping(value = "/test7", method = RequestMethod.GET)
    @ResponseStatus(code = HttpStatus.GATEWAY_TIMEOUT)
    public void s500567() {
        LOG.info("GATEWAY_TIMEOUT ");
    }

    @RequestMapping(value = "/test8", method = RequestMethod.GET)
    @ResponseStatus(code = HttpStatus.REQUEST_TIMEOUT)
    public void s5005678() {
        LOG.info("REQUEST_TIMEOUT ");
    }

    @RequestMapping(value = "/xx", method = RequestMethod.GET)
    public String t500() {

        return "yes";
    }

}
