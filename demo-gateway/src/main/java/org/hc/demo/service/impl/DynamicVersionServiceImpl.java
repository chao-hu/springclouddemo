/**
 * @Title: DynamicVwesionServiceImpl.java
 * @Package org.hc.demo.service.impl
 * @Description: TODO(用一句话描述该文件做什么)
 * @author huchao
 * @date 2019年11月29日
 * @version V1.0
 */
package org.hc.demo.service.impl;

import java.util.Date;
import java.util.List;

import org.hc.demo.entity.DynamicVersion;
import org.hc.demo.repository.DynamicVersionRepository;
import org.hc.demo.service.IVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: DynamicVwesionServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author huchao
 * @date 2019年11月29日
 *
 */
@Service
public class DynamicVersionServiceImpl implements IVersionService {

    @Autowired
    DynamicVersionRepository dynamicVersionRepository;

    /**
     * @Title: add
     * @Description:
     * @param version
     * @return
     * @see org.hc.demo.service.IVersionService#add(org.hc.demo.entity.DynamicVersion)
     */
    @Override
    public DynamicVersion add(DynamicVersion version) {

        version.setCreateTime(new Date());
        return dynamicVersionRepository.save(version);
    }

    /**
     * @Title: delete
     * @Description:
     * @param id
     * @see org.hc.demo.service.IVersionService#delete(java.lang.Long)
     */
    @Override
    public void delete(Long id) {

        dynamicVersionRepository.deleteById(id);
    }

    /**
     * @Title: lastVersion
     * @Description:
     * @return
     * @see org.hc.demo.service.IVersionService#lastVersion()
     */
    @Override
    public Long lastVersion() {

        DynamicVersion version = dynamicVersionRepository.findTopOneByOrderByIdDesc();

        return version.getId();
    }

    /**
     * @Title: list
     * @Description:
     * @return
     * @see org.hc.demo.service.IVersionService#list()
     */
    @Override
    public List<DynamicVersion> list() {

        return dynamicVersionRepository.findAll();
    }

}
