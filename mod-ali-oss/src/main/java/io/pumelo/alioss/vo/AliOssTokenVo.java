package io.pumelo.alioss.vo;

import java.io.Serializable;

/**
 * author Pumelo
 * created at 2017/9/16.
 * 阿里云oss服务签名对象
 */
public class AliOssTokenVo implements Serializable{
    private String accessId;
    private String policy;
    private String signature;
    private String dir;
    private String host;
    private String expire;

    public AliOssTokenVo() {
    }

    public AliOssTokenVo(String accessid, String policy, String signature, String dir, String host, String expire) {
        this.accessId = accessid;
        this.policy = policy;
        this.signature = signature;
        this.dir = dir;
        this.host = host;
        this.expire = expire;
    }

    public String getAccessId() {
        return accessId;
    }

    public void setAccessId(String accessId) {
        this.accessId = accessId;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getExpire() {
        return expire;
    }

    public void setExpire(String expire) {
        this.expire = expire;
    }
}
