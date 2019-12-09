package org.hc.demo.gateway.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hc.demo.gateway.dto.GatewayRouteDefinition;
import org.hc.demo.gateway.entity.DynamicVersion;
import org.hc.demo.gateway.entity.GatewayRoutes;
import org.hc.demo.gateway.repository.DynamicVersionRepository;
import org.hc.demo.gateway.repository.GatewayRoutesRepository;
import org.hc.demo.gateway.service.IRouteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName: GatewayRoutesServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author huchao
 * @date 2019年11月29日
 *
 */
@Service
public class GatewayRoutesServiceImpl implements IRouteService {

    private static Logger logger = LoggerFactory.getLogger(GatewayRoutesServiceImpl.class);

    private static final String dynamicRouteServerName = "demo-gateway";

    @Autowired
    GatewayRoutesRepository gatewayRoutesRepository;

    @Autowired
    DynamicVersionRepository dynamicVersionRepository;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * @Title: add
     * @Description:
     * @param route
     * @return
     * @see org.hc.demo.service.IRouteService#add(org.hc.demo.entity.GatewayRoutes)
     */
    @Override
    public GatewayRoutes add(GatewayRoutes route) {

        route.setDel(false);
        route.setUpdateTime(new Date());
        route.setCreateTime(new Date());

        return gatewayRoutesRepository.save(route);
    }

    /**
     * @Title: update
     * @Description:
     * @param route
     * @return
     * @see org.hc.demo.service.IRouteService#update(org.hc.demo.entity.GatewayRoutes)
     */
    @Override
    public GatewayRoutes update(GatewayRoutes route) {

        route.setUpdateTime(new Date());

        return gatewayRoutesRepository.save(route);
    }

    /**
     * @Title: delete
     * @Description:
     * @param id
     * @see org.hc.demo.service.IRouteService#delete(java.lang.Long)
     */
    @Override
    public void delete(Long id) {

        gatewayRoutesRepository.deleteById(id);
    }

    /**
     * @Title: getRouteDefinitions
     * @Description:
     * @return
     * @see org.hc.demo.service.IRouteService#getRouteDefinitions()
     */
    @Override
    public List<GatewayRouteDefinition> getRouteDefinitions(Boolean isEnable) {

        List<GatewayRoutes> list = getGatewayRoutes(isEnable);

        List<GatewayRouteDefinition> ret = new ArrayList<>();
        for (GatewayRoutes route : list) {

            GatewayRouteDefinition grd = new GatewayRouteDefinition();
            grd.setId(route.getRouteId());
            grd.setUri(route.getRouteUri());
            grd.setOrder(route.getRouteOrder());
            grd.setPredicates(route.getGatewayPredicateDefinition());
            grd.setFilters(route.getGatewayFilterDefinition());
            ret.add(grd);
        }

        return ret;
    }

    @Override
    public List<GatewayRoutes> getGatewayRoutes(Boolean isEnable) {
        List<GatewayRoutes> list = new ArrayList<GatewayRoutes>();
        if (isEnable != null && isEnable) {

            list = gatewayRoutesRepository.findByIsEblAndIsDel(isEnable, false);
        } else {

            list = gatewayRoutesRepository.listNotDeleted();
        }

        return list;
    }

    /**
     * @Title: get
     * @Description:
     * @param id
     * @return
     * @see org.hc.demo.service.IRouteService#get(java.lang.Long)
     */
    @Override
    public GatewayRoutes get(Long id) {

        return gatewayRoutesRepository.getOne(id);
    }

    /**
     * @Title: enableById
     * @Description:
     * @param id
     * @see org.hc.demo.service.IRouteService#enableById(java.lang.Long)
     */
    @Override
    public void enableById(Long id) {

        GatewayRoutes route = gatewayRoutesRepository.getOne(id);
        route.setEbl(true);
        gatewayRoutesRepository.save(route);
    }

    @Override
    @Transactional
    public void syncGatewayRouteDefinition() {

        // 1、修改版本
        changeDynamicVersion();
        // 2、同步非删除启用的路由
        syncEableGatewayRouteDefinition();
        // 3、同步删除、不启用的路由
        syncDelGatewayRouteDefinition();
    }

    /**
     * 修改版本
     * @Title: changeDynamicVersion
     * @Description: 修改版本
     * @throws
     */
    private void changeDynamicVersion() {
        DynamicVersion version = new DynamicVersion();
        version.setCreateTime(new Date());
        dynamicVersionRepository.save(version);
    }

    /**
     * 同步非删除启用的路由
     * @Title: syncEableGatewayRouteDefinition
     * @Description: 同步非删除启用的路由
     * @throws
     */
    private void syncEableGatewayRouteDefinition() {

        List<GatewayRoutes> list = gatewayRoutesRepository.findByIsEblAndIsDel(true, false);
        if (null != list && !list.isEmpty()) {
            for (GatewayRoutes route : list) {
                GatewayRouteDefinition grd = new GatewayRouteDefinition();
                grd.setId(route.getRouteId());
                grd.setUri(route.getRouteUri());
                grd.setOrder(route.getRouteOrder());
                if (null == route.getGatewayPredicateDefinition() || null == route.getGatewayFilterDefinition()) {
                    throw new NullPointerException();
                }
                grd.setPredicates(route.getGatewayPredicateDefinition());
                grd.setFilters(route.getGatewayFilterDefinition());
                String url = "http://" + dynamicRouteServerName + "/actuator/gateway/routes/" + route.getRouteId();
                String res = restTemplate.postForObject(url, grd, String.class);
                logger.info("============================================res:" + res);
            }
        }
    }

    /**
     * 同步删除、不启用的路由
     * @Title: syncDelGatewayRouteDefinition
     * @Description: 同步删除、不启用的路由
     * @throws
     */
    private void syncDelGatewayRouteDefinition() {
        List<GatewayRoutes> list = gatewayRoutesRepository.findByIsEblOrIsDel(false, true);
        if (null != list && !list.isEmpty()) {
            for (GatewayRoutes route : list) {
                String url = "http://" + dynamicRouteServerName + "/actuator/gateway/routes/" + route.getRouteId();
                restTemplate.delete(url);
            }
        }
    }

}
