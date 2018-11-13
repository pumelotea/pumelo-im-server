package io.pumelo.alioss.config;

import com.aliyun.oss.OSSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * author Pumelo
 * created at 2017/9/16.
 */
@Configuration
public class AliOssConfig {

    @Value("${alioss.endpoint}")
    private String endpoint;

    @Value("${alioss.accessId}")
    private String accessId;

    @Value("${alioss.accessKey}")
    private String accessKey;

    @Value("${alioss.bucket}")
    private String bucket;

    @Value("${alioss.dir.head}")
    private String dirHead;

    @Value("${alioss.dir.common}")
    private String dirCommon;

    private String host;

    @Bean
    public OSSClient getOSSClient(){
        host = "http://" + bucket + "." + endpoint;
        return new OSSClient(endpoint, accessId, accessKey);
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessId() {
        return accessId;
    }

    public void setAccessId(String accessId) {
        this.accessId = accessId;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getDirHead() {
        return dirHead;
    }

    public void setDirHead(String dirHead) {
        this.dirHead = dirHead;
    }

    public String getDirCommon() {
        return dirCommon;
    }

    public void setDirCommon(String dirCommon) {
        this.dirCommon = dirCommon;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
