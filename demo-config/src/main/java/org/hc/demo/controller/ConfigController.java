/**
 * @Title: ConfigController.java
 * @Package org.hc.demo
 * @Description: TODO(用一句话描述该文件做什么)
 * @author huchao
 * @date 2019年11月27日
 * @version V1.0
 */
package org.hc.demo.controller;

import java.math.BigInteger;
import java.util.List;

import org.hc.demo.config.entity.Config;
import org.hc.demo.config.service.IConfigService;
import org.hc.demo.utils.RestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: ConfigController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author huchao
 * @date 2019年11月27日
 *
 */
@RestController
@RequestMapping("/v1")
public class ConfigController {

    @Autowired
    IConfigService configServiceImpl;

    @RequestMapping(value = { "/", "/list" })
    public Object list() {

        List<Config> list = configServiceImpl.list();

        return RestUtils.buildRes(list);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Object post(Config config) {
        int code = 0;
        String msg = "添加成功";

        try {

            configServiceImpl.add(config);

        } catch (Exception e) {

            code = -1;
            msg = e.getMessage();
        }

        return RestUtils.buildRes(code, msg);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public Object put(Config config) {
        int code = 0;
        String msg = "修改成功";
        try {

            configServiceImpl.add(config);

        } catch (Exception e) {

            code = -1;
            msg = e.getMessage();
        }

        return RestUtils.buildRes(code, msg);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Object delete(@PathVariable(name = "id", required = true) BigInteger id) {

        int code = 0;
        String msg = "删除成功";
        try {

            configServiceImpl.delete(id);

        } catch (Exception e) {

            code = -1;
            msg = e.getMessage();
        }

        return RestUtils.buildRes(code, msg);
    }
}
