package io.pumelo.sms;

/**
 * author Pumelo
 * created at 2017/10/31.
 */
public class SmsVo {
    private String message;
    private boolean success;

    public SmsVo(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
