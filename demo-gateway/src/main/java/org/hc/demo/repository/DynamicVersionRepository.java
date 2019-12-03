/**
 * @Title: DynamicVersionRepository.java
 * @Package org.hc.demo.repository
 * @Description: TODO(用一句话描述该文件做什么)
 * @author huchao
 * @date 2019年11月28日
 * @version V1.0
 */
package org.hc.demo.repository;

import org.hc.demo.entity.DynamicVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: DynamicVersionRepository
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author huchao
 * @date 2019年11月28日
 *
 */
@Repository
public interface DynamicVersionRepository extends JpaRepository<DynamicVersion, Long> {

    public DynamicVersion findTopOneByOrderByIdDesc();
}
