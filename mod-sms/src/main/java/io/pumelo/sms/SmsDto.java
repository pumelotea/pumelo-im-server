package io.pumelo.sms;

/**
 * author Pumelo
 * created at 2017/10/31.
 */
public class SmsDto {
    private String phone;
    private String code;
    private long timestamp;

    public SmsDto() {
    }

    public SmsDto(String phone, String code, long timestamp) {
        this.phone = phone;
        this.code = code;
        this.timestamp = timestamp;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
