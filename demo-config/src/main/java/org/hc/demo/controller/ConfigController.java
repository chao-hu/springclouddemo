/**
 * @Title: ConfigController.java
 * @Package org.hc.demo
 * @Description: TODO(用一句话描述该文件做什么)
 * @author huchao
 * @date 2019年11月27日
 * @version V1.0
 */
package org.hc.demo;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: ConfigController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author huchao
 * @date 2019年11月27日
 *
 */
@RestController
public class ConfigController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping(value = { "/", "/list" })
    public Object list() {

        List<Config> list = jdbcTemplate.query("select * from properties", (RowMapper<Config>) (rs, rowNum) -> {

            Config conf = new Config();
            conf.setId(rs.getBigDecimal("id").toBigInteger());
            conf.setKey(rs.getString("key1"));
            conf.setValue(rs.getString("value1"));
            conf.setApplication(rs.getString("application"));
            conf.setProfile(rs.getString("profile"));
            conf.setLabel(rs.getString("label"));
            return conf;
        });
        return buildRet(list);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Object post(Config config) {
        int code = 0;
        String msg = "添加成功";
        String sql = "insert into properties(`key1`,`value1`,`application`,`profile`,`label`) values(?,?,?,?,?)";
        try {

            jdbcTemplate.update(sql, new Object[] { config.getKey(), config.getValue(), config.getApplication(),
                    config.getProfile(), config.getLabel() });
        } catch (Exception e) {

            code = -1;
            msg = e.getMessage();
        }

        return buildRet(code, msg);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public Object put(Config config) {
        int code = 0;
        String msg = "修改成功";
        String sql = "update properties set `key1` =?, `value1`=?,`application`=?,`profile`=?,`label`=?) where id=?";
        try {

            jdbcTemplate.update(sql, new Object[] { config.getKey(), config.getValue(), config.getApplication(),
                    config.getProfile(), config.getLabel(), config.getId() });
        } catch (Exception e) {

            code = -1;
            msg = e.getMessage();
        }

        return buildRet(code, msg);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Object delete(@PathVariable(name = "id", required = true) BigInteger id) {

        int code = 0;
        String msg = "删除成功";
        try {

            jdbcTemplate.update("delete properties where id = ?", id);
        } catch (Exception e) {
            // TODO: handle exception
            code = -1;
            msg = e.getMessage();
        }

        return buildRet(code, msg);
    }

    public Object buildRet(Object data) {

        Map<String, Object> map = new HashMap<>();

        map.put("code", 0);
        map.put("msg", "");
        map.put("data", data);

        return map;
    }

    public Object buildRet(int code, String msg) {

        Map<String, Object> map = new HashMap<>();

        map.put("code", code);
        map.put("msg", msg);

        return map;
    }
}
