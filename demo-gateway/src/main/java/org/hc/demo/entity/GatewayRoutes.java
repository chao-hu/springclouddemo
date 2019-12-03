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
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hc.demo.dto.GatewayFilterDefinition;
import org.hc.demo.dto.GatewayPredicateDefinition;
import org.hc.demo.utils.JsonUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name = "`GATEWAY_ROUTES`")
public class GatewayRoutes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "`ROUTE_ID`", length = 64)
    private String routeId;

    @Column(name = "`ROUTE_URI`", length = 128)
    private String routeUri;

    @Column(name = "`ROUTE_ORDER`")
    private Integer routeOrder;

    @Column(name = "`PREDICATES`", length = 2000)
    private String predicates;

    @Column(name = "`FILTERS`", length = 2000)
    private String filters;

    @Column(name = "`IS_EBL`")
    private boolean isEbl;
    @Column(name = "`IS_DEL`")
    private boolean isDel;

    @Column(name = "`CREATE_TIME`")
    private Date createTime;

    @Column(name = "`UPDATE_TIME`")
    private Date updateTime;

    @JsonIgnore
    public List<GatewayPredicateDefinition> getGatewayPredicateDefinition() {

        return JsonUtils.jsonToList(predicates, GatewayPredicateDefinition.class);
    }

    @JsonIgnore
    public List<GatewayFilterDefinition> getGatewayFilterDefinition() {

        return JsonUtils.jsonToList(filters, GatewayFilterDefinition.class);
    }
}
