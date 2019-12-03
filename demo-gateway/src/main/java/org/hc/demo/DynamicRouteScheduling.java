package org.hc.demo;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hc.demo.dto.GatewayFilterDefinition;
import org.hc.demo.dto.GatewayPredicateDefinition;
import org.hc.demo.dto.GatewayRouteDefinition;
import org.hc.demo.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * 定时任务，拉取路由信息
 * 路由信息由路由项目单独维护
 */
@Component
public class DynamicRouteScheduling {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DynamicRouteService dynamicRouteService;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final String dynamicRouteServerName = "dynamic-route-service";

    // 发布路由信息的版本号
    private static Long versionId = 0L;

    // 每60秒中执行一次
    // 如果版本号不相等则获取最新路由信息并更新网关路由
    @Scheduled(cron = "*/60 * * * * ?")
    public void getDynamicRouteInfo() {
        try {
            System.out.println("拉取时间:" + dateFormat.format(new Date()));
            // 先拉取版本信息，如果版本号不想等则更新路由
            Long resultVersionId = restTemplate
                    .getForObject("http://" + dynamicRouteServerName + "/version/lastVersion", Long.class);
            System.out.println("路由版本信息：本地版本号：" + versionId + "，远程版本号：" + resultVersionId);
            if (resultVersionId != null && versionId != resultVersionId) {
                System.out.println("开始拉取路由信息......");
                String resultRoutes = restTemplate
                        .getForObject("http://" + dynamicRouteServerName + "/gateway-routes/routes", String.class);
                System.out.println("路由信息为：" + resultRoutes);
                if (!StringUtils.isEmpty(resultRoutes)) {
                    List<GatewayRouteDefinition> list = JsonUtils.jsonToList(resultRoutes,
                            GatewayRouteDefinition.class);
                    for (GatewayRouteDefinition definition : list) {
                        // 更新路由
                        RouteDefinition routeDefinition = assembleRouteDefinition(definition);
                        dynamicRouteService.update(routeDefinition);
                    }
                    versionId = resultVersionId;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 把前端传递的参数转换成路由对象
    private RouteDefinition assembleRouteDefinition(GatewayRouteDefinition gwdefinition) {
        RouteDefinition definition = new RouteDefinition();
        definition.setId(gwdefinition.getId());
        definition.setOrder(gwdefinition.getOrder());

        // 设置断言
        List<PredicateDefinition> pdList = new ArrayList<>();
        List<GatewayPredicateDefinition> gatewayPredicateDefinitionList = gwdefinition.getPredicates();
        for (GatewayPredicateDefinition gpDefinition : gatewayPredicateDefinitionList) {
            PredicateDefinition predicate = new PredicateDefinition();
            predicate.setArgs(gpDefinition.getArgs());
            predicate.setName(gpDefinition.getName());
            pdList.add(predicate);
        }
        definition.setPredicates(pdList);

        // 设置过滤器
        List<FilterDefinition> filters = new ArrayList();
        List<GatewayFilterDefinition> gatewayFilters = gwdefinition.getFilters();
        for (GatewayFilterDefinition filterDefinition : gatewayFilters) {
            FilterDefinition filter = new FilterDefinition();
            filter.setName(filterDefinition.getName());
            filter.setArgs(filterDefinition.getArgs());
            filters.add(filter);
        }
        definition.setFilters(filters);

        URI uri = null;
        if (gwdefinition.getUri().startsWith("http")) {
            uri = UriComponentsBuilder.fromHttpUrl(gwdefinition.getUri()).build().toUri();
        } else {
            uri = URI.create(gwdefinition.getUri());
        }
        definition.setUri(uri);
        return definition;
    }
}
