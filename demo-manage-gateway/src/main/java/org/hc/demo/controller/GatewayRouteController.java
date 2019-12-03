/**
 * @Title: GatewayRouteController.java
 * @Package org.hc.demo.controller
 * @Description: TODO(用一句话描述该文件做什么)
 * @author huchao
 * @date 2019年11月28日
 * @version V1.0
 */
package org.hc.demo.controller;

import java.text.DateFormat;
import java.util.Date;

import org.hc.demo.gateway.entity.DynamicVersion;
import org.hc.demo.gateway.entity.GatewayRoutes;
import org.hc.demo.gateway.service.IRouteService;
import org.hc.demo.gateway.service.IVersionService;
import org.hc.demo.utils.JsonUtils;
import org.hc.demo.utils.RestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName: GatewayRouteController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author huchao
 * @date 2019年11月28日
 *
 */

@Controller
public class GatewayRouteController {

    public static final String versionKey = "gateway-last-version";
    public static final String routeKey = "gateway-dynamic-route";

    @RequestMapping("/hello")
    public String hello(@RequestParam(name = "name", required = false, defaultValue = "huchao") String name, Model m) {

        m.addAttribute("now", DateFormat.getDateTimeInstance().format(new Date()));
        return "hello";
    }

    @Autowired
    private IVersionService versionService;

    @Autowired
    private IRouteService routeService;

    @RequestMapping(value = "/version", method = RequestMethod.POST)
    @ResponseBody
    public Object addVersion(ModelMap map) {

        int code = 200;
        String msg = "";
        try {

            DynamicVersion version = new DynamicVersion();

            versionService.add(version);

            return RestUtils.buildRes(version);
        } catch (Exception e) {

            code = -1;
            msg = "添加失败，" + e.getMessage();
        }

        return RestUtils.buildRes(code, msg);
    }

    // 获取最后一次发布的版本号
    @RequestMapping(value = "/version/lastVersion", method = RequestMethod.GET)
    @ResponseBody
    public Object getLastVersion() {

        Long versionId = versionService.lastVersion();

        return RestUtils.buildRes(versionId);
    }

    // 打开发布版本列表页面
    @RequestMapping("/version")
    public String listAll(ModelMap map) {

        map.addAttribute("list", versionService.list());
        return "versionlist";
    }

    /**
     * 获取所有动态路由信息
     * @return
     */
    @RequestMapping("/routes")
    @ResponseBody
    public Object getRouteDefinitions() {

        String result = JsonUtils.objectToJson(routeService.getRouteDefinitions(true));

        return result;
    }

    // 打开添加路由页面
    @RequestMapping(value = "/routes/add", method = RequestMethod.GET)
    public String addPage(ModelMap map) {
        map.addAttribute("route", new GatewayRoutes());
        return "addRoute";
    }

    // 添加路由信息
    @RequestMapping(value = "/routes", method = RequestMethod.POST)
    @ResponseBody
    public Object add(@RequestBody GatewayRoutes route) {

        int code = 200;
        String msg = "";
        try {

            routeService.add(route);

            return RestUtils.buildRes(route);
        } catch (Exception e) {

            code = -1;
            msg = "添加失败，" + e.getMessage();
        }

        return RestUtils.buildRes(code, msg);
    }

    @RequestMapping(value = "/routes/edit", method = RequestMethod.GET)
    public String editPage(ModelMap map, Long id) {
        map.addAttribute("route", routeService.get(id));
        return "addRoute";
    }

    // 添加路由信息
    @RequestMapping(value = "/routes", method = RequestMethod.PUT)
    @ResponseBody
    public Object edit(@RequestBody GatewayRoutes route) {

        int code = 200;
        String msg = "";
        try {

            routeService.update(route);

            return RestUtils.buildRes(route);
        } catch (Exception e) {

            code = -1;
            msg = "编辑失败，" + e.getMessage();
        }

        return RestUtils.buildRes(code, msg);
    }

    // 打开路由列表
    @RequestMapping("/list")
    public String list(ModelMap map) {

        map.addAttribute("list", routeService.getRouteDefinitions(null));
        return "routelist";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Object delete(Long id) {

        int code = 200;
        String msg = "";
        try {

            routeService.delete(id);
        } catch (Exception e) {

            code = -1;
            msg = "删除失败， " + e.getMessage();
        }

        return RestUtils.buildRes(code, msg);
    }
}
