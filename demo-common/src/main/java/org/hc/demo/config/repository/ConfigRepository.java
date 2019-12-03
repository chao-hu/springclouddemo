/**
 * @Title: ConfigRepository.java
 * @Package com.hc.demo.config.repository
 * @Description: TODO(用一句话描述该文件做什么)
 * @author huchao
 * @date 2019年12月3日
 * @version V1.0
 */
package org.hc.demo.config.repository;

import java.math.BigInteger;

import org.hc.demo.config.entity.Config;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: ConfigRepository
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author huchao
 * @date 2019年12月3日
 *
 */
@Repository
public interface ConfigRepository extends JpaRepository<Config, BigInteger> {

}
