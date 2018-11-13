package io.pumelo.authorizion;


/**
 * token拦截器接口
 */
public interface Security {
    /**
     * jwtId 一般用来用来表示客户端定义的appid.
     * @return
     */
    String getJwtId();

    /**
     * 从消息头的accessToken获取用户信息。
     * 然后用对应服务中各自的user对象解析即可。
     * 同时如果发生错误 该字符串就会解析成错误信息。
     * @return
     */
    String getSubjectFromAccessToken();


    boolean isTimeout();

    /**
     * 检查token是否有限
     * @return
     */
    boolean isAccessTokenInvaild();

    /**
     * 获取用户客户端ip地址
     * @return
     */
    String getIp();

    /**
     * 获取用户客户端mac地址
     * @return
     */
    String getMac();

}
