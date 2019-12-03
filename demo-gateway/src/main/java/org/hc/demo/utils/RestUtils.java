package org.hc.demo.utils;

import org.hc.demo.ApiResult;

/**
 *
 */
public class RestUtils {

    public static ApiResult buildRes(Object data) {

        ApiResult res = new ApiResult();
        res.setCode(0);
        res.setData(data);

        return res;
    }

    public static ApiResult buildRes(int code, String msg) {

        ApiResult res = new ApiResult();
        res.setCode(code);
        res.setMessage(msg);

        return res;
    }
}
