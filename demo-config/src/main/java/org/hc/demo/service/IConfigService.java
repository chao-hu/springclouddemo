/**
 * @Title: IConfigService.java
 * @Package com.hc.demo.config.service
 * @Description: TODO(用一句话描述该文件做什么)
 * @author huchao
 * @date 2019年12月3日
 * @version V1.0
 */
package org.hc.demo.service;

import java.math.BigInteger;
import java.util.List;

import org.hc.demo.entity.Config;

/**
 * @ClassName: IConfigService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author huchao
 * @date 2019年12月3日
 *
 */
public interface IConfigService {

    public Config add(Config conf);

    public Config update(Config conf);

    public Config get(BigInteger id);

    public void delete(BigInteger id);

    public List<Config> list();
}
