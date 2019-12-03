/**
 * @Title: IVersionService.java
 * @Package org.hc.demo.service
 * @Description: TODO(用一句话描述该文件做什么)
 * @author huchao
 * @date 2019年11月29日
 * @version V1.0
 */
package org.hc.demo.service;

import java.util.List;

import org.hc.demo.entity.DynamicVersion;

/**
 * @ClassName: IVersionService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author huchao
 * @date 2019年11月29日
 *
 */
public interface IVersionService {

    public DynamicVersion add(DynamicVersion version);

    public void delete(Long id);

    public Long lastVersion();

    public List<DynamicVersion> list();
}
