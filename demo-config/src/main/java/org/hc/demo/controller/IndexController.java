/**
 * @Title: ConfigController.java
 * @Package org.hc.demo
 * @Description: TODO(用一句话描述该文件做什么)
 * @author huchao
 * @date 2019年11月27日
 * @version V1.0
 */
package org.hc.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hc.demo.config.entity.Config;
import org.hc.demo.config.service.IConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName: IndexController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author huchao
 * @date 2019年11月27日
 *
 */
@Controller
public class IndexController {

    @Autowired
    IConfigService configService;

    @RequestMapping(value = { "/", "/list" })
    public String index(HttpServletRequest request, Model m) {

        List<Config> list = configService.list();

        m.addAttribute("list", list);
        return "list";
    }
}
