package org.hc.demo.service.impl;

import java.math.BigInteger;
import java.util.List;

import org.hc.demo.entity.Config;
import org.hc.demo.repository.ConfigRepository;
import org.hc.demo.service.IConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: ConfigServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author huchao
 * @date 2019年12月3日
 *
 */
@Service
public class ConfigServiceImpl implements IConfigService {

    @Autowired
    ConfigRepository configRepository;

    /**
     * @Title: add
     * @Description:
     * @param conf
     * @return
     * @see org.hc.demo.config.service.IConfigService#add(org.hc.demo.config.entity.Config)
     */
    @Override
    public Config add(Config conf) {

        return configRepository.save(conf);
    }

    /**
     * @Title: update
     * @Description:
     * @param conf
     * @return
     * @see org.hc.demo.config.service.IConfigService#update(org.hc.demo.config.entity.Config)
     */
    @Override
    public Config update(Config conf) {

        return configRepository.save(conf);
    }

    /**
     * @Title: get
     * @Description:
     * @param id
     * @return
     * @see org.hc.demo.config.service.IConfigService#get(java.lang.String)
     */
    @Override
    public Config get(BigInteger id) {

        return configRepository.getOne(id);
    }

    /**
     * @Title: list
     * @Description:
     * @return
     * @see org.hc.demo.config.service.IConfigService#list()
     */
    @Override
    public List<Config> list() {

        return configRepository.findAll();
    }

    /**
     * @Title: delete
     * @Description:
     * @param id
     * @see org.hc.demo.config.service.IConfigService#delete(java.lang.String)
     */
    @Override
    public void delete(BigInteger id) {

        configRepository.deleteById(id);
    }

}
