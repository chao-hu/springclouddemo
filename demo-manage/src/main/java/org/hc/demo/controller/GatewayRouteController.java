/**
 * @Title: GatewayRouteController.java
 * @Package org.hc.demo.controller
 * @Description: TODO(用一句话描述该文件做什么)
 * @author huchao
 * @date 2019年11月28日
 * @version V1.0
 */
package org.hc.demo.controller;

import java.util.List;

import org.hc.demo.gateway.dto.GatewayRouteDefinition;
import org.hc.demo.gateway.entity.DynamicVersion;
import org.hc.demo.gateway.entity.GatewayRoutes;
import org.hc.demo.gateway.service.IRouteService;
import org.hc.demo.gateway.service.IVersionService;
import org.hc.demo.utils.RestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: GatewayRouteController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author huchao
 * @date 2019年11月28日
 *
 */

@RestController
public class GatewayRouteController {

    public static final String versionKey = "gateway-last-version";
    public static final String routeKey = "gateway-dynamic-route";

    @Autowired
    private IVersionService versionService;

    @Autowired
    private IRouteService routeService;

    @RequestMapping(value = "/version", method = RequestMethod.POST)
    public Object addVersion(ModelMap map) {

        int code = 0;
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

    /**
     * 获取最后一次发布的版本号
     * @Title: getLastVersion
     * @Description: 获取最后一次发布的版本号
     * @return Object
     * @throws
     */
    @RequestMapping(value = "/version/lastVersion", method = RequestMethod.GET)
    public Object getLastVersion() {

        Long versionId = versionService.lastVersion();

        return RestUtils.buildRes(versionId);
    }

    /**
     * 获取所有动态路由信息
     * @return
     */
    @RequestMapping("/routes")
    public Object getRouteDefinitions() {

        int code = 0;
        String msg = "";
        try {

            List<GatewayRouteDefinition> list = routeService.getRouteDefinitions(true);

            return RestUtils.buildRes(list);
        } catch (Exception e) {

            code = -1;
            msg = "查询失败，" + e.getMessage();
        }

        return RestUtils.buildRes(code, msg);

    }

    /**
     * 获取动态路由列表
     * @Title: getGatewayRoutes
     * @Description: 获取动态路由列表
     * @return Object
     * @throws
     */
    @RequestMapping("/gatewayroutes")
    public Object getGatewayRoutes() {

        int code = 0;
        String msg = "";
        try {

            List<GatewayRoutes> list = routeService.getGatewayRoutes(null);

            return RestUtils.buildRes(list);
        } catch (Exception e) {

            code = -1;
            msg = "查询失败，" + e.getMessage();
        }

        return RestUtils.buildRes(code, msg);

    }

    /**
     * 获取单个动态路由信息
     * @Title: getRoute
     * @Description: 获取单个动态路由信息
     * @param id
     * @return Object
     * @throws
     */
    @RequestMapping("/routes/{id}")
    public Object getRoute(@PathVariable(name = "id", required = true) Long id) {

        int code = 0;
        String msg = "";
        try {

            GatewayRoutes gatewayRoutes = routeService.get(id);

            return RestUtils.buildRes(gatewayRoutes);
        } catch (Exception e) {

            code = -1;
            msg = "查询失败，" + e.getMessage();
        }

        return RestUtils.buildRes(code, msg);

    }

    /**
     * 同步路由信息
     * @Title: syncGatewayRoutes
     * @Description: 同步路由信息
     * @return Object
     * @throws
     */
    @RequestMapping("/routes/sync")
    public Object syncGatewayRoutes() {

        int code = 0;
        String msg = "同步成功";
        try {

            routeService.syncGatewayRouteDefinition();

            return RestUtils.buildRes(code, msg);
        } catch (Exception e) {

            code = -1;
            msg = "同步失败，" + e.getMessage();
        }

        return RestUtils.buildRes(code, msg);

    }

    /**
     * 添加路由信息
     * @Title: add
     * @Description: 添加路由信息
     * @param route
     * @return Object
     * @throws
     */
    @RequestMapping(value = "/routes", method = RequestMethod.POST)
    public Object add(@RequestBody GatewayRoutes route) {

        int code = 0;
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

    /**
     * 修改路由信息
     * @Title: edit
     * @Description: 修改路由信息
     * @param route
     * @return Object
     * @throws
     */
    @RequestMapping(value = "/routes", method = RequestMethod.PUT)
    public Object edit(@RequestBody GatewayRoutes route) {

        int code = 0;
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

    @RequestMapping(value = "/routes", method = RequestMethod.DELETE)
    public Object delete(Long id) {

        int code = 0;
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
