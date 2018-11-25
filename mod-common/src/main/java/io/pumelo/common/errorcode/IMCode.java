package io.pumelo.common.errorcode;


public enum IMCode implements BaseErrorCode {
    SUCCESS(0,"操作成功！"),
    FAIL(-1,"操作失败！"),
    TOO_BUSY(-2,"服务器繁忙"),
    SC_OK(200,"OPERATE RETRIEVE SUCCESS"),
    CREATE_UPDATE_SUCCESS(201,"OPERATE UPDATE SUCCESS"),
    DELETE_SUCCESS(204,"OPERATE DELETE SUCCESS"),
    CLIENT_BAD_REQUEST(400, "Bad Request!"),
    CLIENT_NOT_AUTHORIZATION(401, "Not Authorization"),
    SC_NOT_FOUND(404,"404 error, urlpath not found "),
    CLIENT_METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
    CLIENT_NO_ACCEPTABLE(406, "Not Acceptable"),
    SERVER_ERROR(500, "Internal Server Error"),

    SERVER_RUNNTIME_EXCEPTION(100000, "[服务器]运行时异常"),
    SERVER_NULL_EXCEPTION(100001, "[服务器]空值异常"),
    SERVER_CLASSCAST_EXCEPTION(100002, "[服务器]数据类型转换异常"),
    SERVER_IO_EXCEPTION(100003, "[服务器]IO异常"),
    SERVER_NOSUCHMETHOD_EXCEPTION(100004, "[服务器]未知方法异常"),
    SERVER_INDEXOUTOFBOUNDS_EXCEPTION(100005, "[服务器]数组越界异常"),
    SERVER_NETWORK_EXCEPTION(100006, "[服务器]网络异常"),


    ACCOUNT_NOT_EXISTS(1001,"用户不存在"),
    ACCOUNT_PWD_ERROR(1002,"密码错误"),
    LOGIN_FAIL(1003,"登录失败"),
    FRIEND_NOT_EXISTS(2001,"好友不存在"),
    FRIEND_EXISTS(2002,"好友已存在"),
    GROUP_NOT_EXISTS(3001,"群组不存在"),
    MEMBER_NOT_EXISTS(4001,"成员不存在"),
    MEMBER_EXISTS(4002,"成员已存在"),
    ADMIN_EXIT_BAN(4002,"管理员禁止退出"),
    YOU_NOT_IN_GROUP(4003,"您不在该群组"),
    YOU_IN_GROUP(4004,"您已在该群组"),



            ;
    private int code;
    private String msg;

    IMCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "{" +
                "code:" + code +
                ", msg:" + msg + "}";
    }
}
