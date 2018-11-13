package io.pumelo.alioss.service;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import io.pumelo.alioss.config.AliOssConfig;
import io.pumelo.alioss.vo.AliOssTokenVo;
import io.pumelo.common.basebean.ImageType;
import io.pumelo.common.web.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
/**
 * author Pumelo
 * created at 2017/9/16.
 */
@Service
public class AliOssService {
    @Autowired
    private AliOssConfig aliOssConfig;

    @Autowired
    private OSSClient client;

    public ApiResponse<AliOssTokenVo> getOSSToken(ImageType imageType) throws UnsupportedEncodingException {
//        String endpoint = "oss-cn-shanghai.aliyuncs.com";
//        String accessId = "accessId ";
//        String accessKey = "accessKey ";
//        String bucket = "bucket ";
        String dir = "picture/";
//        String host = "http://" + bucket + "." + endpoint;
        switch (imageType){
            case COMMON_IMG: dir = aliOssConfig.getDirCommon();break;
            case HEAD_IMG: dir =aliOssConfig.getDirHead();break;
        }

        long expireTime = 30;
        long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
        Date expiration = new Date(expireEndTime);
        PolicyConditions policyConds = new PolicyConditions();
        policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
        policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);
        String postPolicy = client.generatePostPolicy(expiration, policyConds);
        byte[] binaryData = postPolicy.getBytes("utf-8");
        String encodedPolicy = BinaryUtil.toBase64String(binaryData);
        String postSignature = client.calculatePostSignature(postPolicy);
        AliOssTokenVo aliOssTokenVo = new AliOssTokenVo(
                aliOssConfig.getAccessId(),
                encodedPolicy,
                postSignature,
                dir,
                aliOssConfig.getHost(),
                String.valueOf(expireEndTime / 1000)
        );
        return ApiResponse.ok(aliOssTokenVo);
    }
}
