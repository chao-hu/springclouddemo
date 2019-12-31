package org.hc.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class HelloService {
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 简单用法
     */
    @HystrixCommand
    public String hiService(String name) {
        String url = "http://demo-provider/provider/hi?name=" + name;
        String res = restTemplate.getForObject(url, String.class);

        return res;
    }

    /**
     * 定制超时
     */
    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000") })
    public String heyService() {
        return restTemplate.getForObject("http://demo-provider/provider/hi?name=hey", String.class);
    }

    /**
     * 定制降级方法
     */
    @HystrixCommand(fallbackMethod = "getFallback")
    public String ahService() {
        return restTemplate.getForObject("http://demo-provider/provider/ah", String.class);
    }

    /**
     * 定制线程池隔离策略
     */
    @HystrixCommand(fallbackMethod = "getFallback", threadPoolKey = "studentServiceThreadPool", threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "30"), @HystrixProperty(name = "maxQueueSize", value = "50") })
    public String ohService() {
        return restTemplate.getForObject("http://demo-provider/provider/hi?name=oh", String.class);
    }

    public String getFallback() {
        return "Oh , sorry , error !";
    }

}
