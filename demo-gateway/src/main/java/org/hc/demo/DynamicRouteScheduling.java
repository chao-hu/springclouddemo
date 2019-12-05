package org.hc.demo;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.hc.demo.common.ApiResult;
import org.hc.demo.gateway.dto.GatewayFilterDefinition;
import org.hc.demo.gateway.dto.GatewayPredicateDefinition;
import org.hc.demo.gateway.dto.GatewayRouteDefinition;
import org.hc.demo.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * 定时任务，拉取路由信息
 * 路由信息由路由项目单独维护
 */
@Component
public class DynamicRouteScheduling {

    private static Logger logger = LoggerFactory.getLogger(DynamicRouteScheduling.class);

    @Autowired
    private DynamicRouteService dynamicRouteService;

    private static final String dynamicRouteServerName = "demo-manager";

    @Autowired
    private DiscoveryClient discoveryClient;

    private String getUri(String application) {

        List<ServiceInstance> instances = discoveryClient.getInstances(application);

        if (instances == null || instances.isEmpty()) {

            return null;
        }

        int i = new Random().nextInt(instances.size());

        return instances.get(i).getUri().toString();
    }

    // 发布路由信息的版本号
    private static Long versionId = 0L;

    // 每60秒中执行一次
    // 如果版本号不相等则获取最新路由信息并更新网关路由
    @Scheduled(cron = "*/60 * * * * ?")
    public void getDynamicRouteInfo() {

        String uri = getUri(dynamicRouteServerName);
        if (uri == null || uri.length() < 1) {

            logger.info("拉取失败, 获取不到uri");
            return;
        }

        String versionUri = uri + "/manager/version/lastVersion";
        String routeUri = uri + "/manager/routes";

        try {
            logger.info("正在拉取" + versionUri + " " + routeUri);

            // 先拉取版本信息，如果版本号不想等则更新路由
            ApiResult result = WebClient.create().get().uri(versionUri).retrieve().bodyToMono(ApiResult.class).block();

            Long resultVersionId = ((Integer) result.getData()).longValue();

            logger.info("路由版本信息：本地版本号：" + versionId + "，远程版本号：" + resultVersionId);
            if (resultVersionId != null && versionId != resultVersionId) {
                logger.info("开始拉取路由信息......");

                ApiResult result2 = WebClient.create().get().uri(routeUri).retrieve().bodyToMono(ApiResult.class)
                        .block();

                logger.info("路由信息为：" + JsonUtils.objectToJson(result2.getData()));
                List<GatewayRouteDefinition> list = JsonUtils.jsonToList(JsonUtils.objectToJson(result2.getData()),
                        GatewayRouteDefinition.class);

                if (list != null && list.size() > 0) {

                    for (GatewayRouteDefinition definition : list) {
                        // 更新路由
                        RouteDefinition routeDefinition = assembleRouteDefinition(definition);
                        dynamicRouteService.update(routeDefinition);
                    }
                    versionId = resultVersionId;
                }
            }
        } catch (Exception e) {

            logger.error(e.getMessage(), e);
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
        if (gatewayPredicateDefinitionList != null && gatewayPredicateDefinitionList.size() > 0) {
            for (GatewayPredicateDefinition gpDefinition : gatewayPredicateDefinitionList) {
                PredicateDefinition predicate = new PredicateDefinition();
                predicate.setArgs(gpDefinition.getArgs());
                predicate.setName(gpDefinition.getName());
                pdList.add(predicate);
            }
        }
        definition.setPredicates(pdList);
        // 设置过滤器
        List<FilterDefinition> filters = new ArrayList();
        List<GatewayFilterDefinition> gatewayFilters = gwdefinition.getFilters();
        if (gatewayFilters != null && gatewayFilters.size() > 0) {
            for (GatewayFilterDefinition filterDefinition : gatewayFilters) {
                FilterDefinition filter = new FilterDefinition();
                filter.setName(filterDefinition.getName());
                filter.setArgs(filterDefinition.getArgs());
                filters.add(filter);
            }
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
