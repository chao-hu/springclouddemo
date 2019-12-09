package org.hc.demo.controller;

import java.text.DateFormat;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @RequestMapping(value = { "/config/list" })
    public String index() {

        return "config-list";
    }

    @RequestMapping(value = { "/route/list" })
    public String gateway() {

        return "route-list";
    }

    @RequestMapping("/hello")
    public String hello(@RequestParam(name = "name", required = false, defaultValue = "huchao") String name, Model m) {

        m.addAttribute("now", DateFormat.getDateTimeInstance().format(new Date()));
        return "hello";
    }
}
