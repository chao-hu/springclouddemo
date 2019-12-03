package org.hc.demo.gateway.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hc.demo.gateway.dto.GatewayRouteDefinition;
import org.hc.demo.gateway.entity.GatewayRoutes;
import org.hc.demo.gateway.repository.GatewayRoutesRepository;
import org.hc.demo.gateway.service.IRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: GatewayRoutesServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author huchao
 * @date 2019年11月29日
 *
 */
@Service
public class GatewayRoutesServiceImpl implements IRouteService {

    @Autowired
    GatewayRoutesRepository gatewayRoutesRepository;

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
        route.setEbl(false);
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

        List<GatewayRoutes> list = new ArrayList<GatewayRoutes>();
        if (isEnable != null && isEnable) {

            list = gatewayRoutesRepository.listOnlyEnableNotDeleted();
        } else {

            list = gatewayRoutesRepository.listNotDeleted();
        }

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

    /**
     * @Title: get
     * @Description:
     * @param id
     * @return
     * @see org.hc.demo.service.IRouteService#get(java.lang.Long)
     */
    @Override
    public GatewayRoutes get(Long id) {

        return gatewayRoutesRepository.findById(id);
    }

    /**
     * @Title: enableById
     * @Description:
     * @param id
     * @see org.hc.demo.service.IRouteService#enableById(java.lang.Long)
     */
    @Override
    public void enableById(Long id) {

        GatewayRoutes route = gatewayRoutesRepository.findById(id);
        route.setEbl(true);
        gatewayRoutesRepository.save(route);
    }

}
