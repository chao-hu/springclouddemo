/**
 * @Title: DynamicVersion.java
 * @Package org.hc.demo.entity
 * @Description: TODO(用一句话描述该文件做什么)
 * @author huchao
 * @date 2019年11月28日
 * @version V1.0
 */
package org.hc.demo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @ClassName: DynamicVersion
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author huchao
 * @date 2019年11月28日
 *
 */
@Entity
@Data
@Table(name = "`DYNAMIC_VERSION`")
public class DynamicVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "`CREATE_TIME`")
    private Date createTime;
}
