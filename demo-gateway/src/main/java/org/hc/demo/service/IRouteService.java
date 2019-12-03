/**
 * @Title: IRouteService.java
 * @Package org.hc.demo.service
 * @Description: TODO(用一句话描述该文件做什么)
 * @author huchao
 * @date 2019年11月29日
 * @version V1.0
 */
package org.hc.demo.service;

import java.util.List;

import org.hc.demo.dto.GatewayRouteDefinition;
import org.hc.demo.entity.GatewayRoutes;

/**
 * @ClassName: IRouteService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author huchao
 * @date 2019年11月29日
 *
 */
public interface IRouteService {

    public GatewayRoutes add(GatewayRoutes route);

    public GatewayRoutes update(GatewayRoutes route);

    public GatewayRoutes get(Long id);

    public void delete(Long id);

    public void enableById(Long id);

    public List<GatewayRouteDefinition> getRouteDefinitions(Boolean isEnable);

}
