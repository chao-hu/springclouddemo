/**
 * @Title: DynamicVersionRepository.java
 * @Package org.hc.demo.repository
 * @Description: TODO(用一句话描述该文件做什么)
 * @author huchao
 * @date 2019年11月28日
 * @version V1.0
 */
package org.hc.demo.repository;

import java.util.List;

import org.hc.demo.entity.GatewayRoutes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: DynamicVersionRepository
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author huchao
 * @date 2019年11月28日
 *
 */
@Repository
public interface GatewayRoutesRepository extends JpaRepository<GatewayRoutes, Integer> {

    public GatewayRoutes findById(Long id);

    public void deleteById(Long id);

    @Query(nativeQuery = true, value = "select * From gateway_routes where is_del != 1")
    public List<GatewayRoutes> listNotDeleted();

    @Query(nativeQuery = true, value = "select * From gateway_routes where is_ebl = 1 and is_del != 1")
    public List<GatewayRoutes> listOnlyEnableNotDeleted();

}
