/**
 * @Title: Config.java
 * @Package org.hc.demo
 * @Description: TODO(用一句话描述该文件做什么)
 * @author huchao
 * @date 2019年11月27日
 * @version V1.0
 */
package org.hc.demo.config.entity;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

/**
 * @ClassName: Config
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author huchao
 * @date 2019年11月27日
 *
 */
@Entity
@Table(name = "`PROPERTIES`")
@Data
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class Config {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(name = "`KEY1`")
    String key;
    @Column(name = "`VALUE1`")
    String value;

    String application;

    String profile;

    String label;

}
