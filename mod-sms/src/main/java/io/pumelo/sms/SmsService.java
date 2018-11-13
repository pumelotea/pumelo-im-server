package io.pumelo.sms;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import io.pumelo.redis.ObjectRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * author Pumelo
 * created at 2017/10/31.
 */
@Service
public class SmsService extends BaseSms{

    @Value("${aliyun.sms.codeSignName}")
    private String codeSignName;
    @Value("${aliyun.sms.codeTemplate}")
    private String codeTemplate;

    @Autowired
    private ObjectRedis objectRedis;

    /**
     * 发送短信验证码
     * @param phone
     */
    public SmsVo sendSmsCode(String phone) {
        if(isSendOften(phone)){
            return new SmsVo("发送短信太频繁",false);
        }
        SmsDto smsDto = makeCode(phone);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",smsDto.getCode());
        jsonObject.put("product","Dysmsapi");
        SendSmsResponse sendSmsResponse;
        try {
            sendSmsResponse = send(phone,codeSignName,codeTemplate,jsonObject);
        } catch (ClientException e) {
            e.printStackTrace();
            return new SmsVo(e.getMessage(),false);
        }

        if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
            //请求成功
            cacheCode(smsDto);
            return new SmsVo("发送短信成功",true);
        }
        return new SmsVo("发送短信失败",false);
    }

    /**
     * 检查发送是否过于频繁
     * @param phone
     * @return
     */
    public boolean isSendOften(String phone){
        //判断是否有已存在短信验证码
        List<SmsDto> smsDtos = objectRedis.getAllObjects("sms_code/"+phone+"/*",SmsDto.class);
        if(smsDtos!=null && smsDtos.size() != 0){
            //次数限制
            if(smsDtos.size()==5){
                return true;
            }
            //时间限制
            for (Iterator<SmsDto> smsDtoIterator = smsDtos.iterator();smsDtoIterator.hasNext();){
                SmsDto sms = smsDtoIterator.next();
                if(sms.getTimestamp() + 60 *1000 > System.currentTimeMillis()){
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 制作验证码
     * @param phone
     * @return
     */
    private SmsDto makeCode(String phone){
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for(int i=0;i<6;i++){
            int next =random.nextInt(10);
            code.append(next);
        }
        return new SmsDto(phone, code.toString(),System.currentTimeMillis());
    }

    /**
     * 缓存验证码15分钟
     * @param dto
     */
    private void cacheCode(SmsDto dto){
        objectRedis.add("sms_code/"+dto.getPhone()+"/"+dto.getTimestamp(),15L,dto);
    }

    /**
     * 验证短信
     * @param phone
     * @param code
     * @return
     */
    public boolean validSmsCode(String phone, String code){
        //取出所有有关该手机号的短信验证码
        List<SmsDto> smsDtos = objectRedis.getAllObjects("sms_code/"+phone+"/*",SmsDto.class);
        if(smsDtos!=null && smsDtos.size() != 0){
            for (SmsDto sms : smsDtos) {
                if (sms.getCode().equals(code)) {
                    objectRedis.delete("sms_code/" + phone + "/" + sms.getTimestamp());
                    return true;
                }
            }
        }
        return false;
    }
}
