package io.pumelo.common.errorcode;

public enum AppCode implements BaseErrorCode {
    SUCCESS(0,"操作成功！"),
    FAIL(-1,"操作失败！"),
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

    /**前端请求的参数错误【一般数参数的格式错误，参数值错误】*/
    DTO_PARAMS_ERROR(60003,"参数错误"),
    /**后端没有查询到任何数据*/
    NOT_CONTENT(60004," DATA EMPTY"),
    /**验证码无效或过期*/
    INVALID_CAPTCHA(60005, "无效验证码"),
    /**Access Token 无效*/
    JWT_TOKEN_INVALID(60006,"无效accesstoken"),
    /**Access Token 过期*/
    JWT_TOKEN_EXPIRAT(60007,"accesstoken过期，请重新登陆"),
    /**重新登录*/
    LOGIN_AGAIN(60008,"长时间未操作，请重新登录"),
    /**用户名或密码错误*/
    ACCOUNT_PWD_ERROR(60009,"账号密码错误"),
    SYSTEM_EXCEPTION(60010, "系统异常"),
    ACCOUNT_EXISTS(60104,"账号已存在"),
    JWT_REFRESHTOKEN_INVALID(60105,"无效refreshtoken"),
    USER_SESSION_EXPIRAT(60106,"用户账号缓存过期"),
    ACCOUNT_IS_NOT_EXISTS(60107, "账号不存在"),


    //-------------------App用户相关------------------//
    LOGIN_FAIL(64001,"登录失败"),
    REGIST_FAIL(64002,"注册失败"),
    USERINFO_GET_FAIL(64003,"用户信息获取失败"),
    USERINFO_UPDATE_FAIL(64004,"用户信息修改失败"),
    RESET_PASSWORD_FAIL(64005,"重置密码失败"),
    MODIFY_PASSWORD_FAIL(64006,"修改密码失败"),
    PHONE_REGISTERED(64007, "该手机号已注册"),
    TIME_FORMAT_ERROR(64008, "生日格式不正确 格式:yyyy-MM-dd"),

    //-------------------设备相关------------------//
    DEVICE_NOT_EXIST(65001, "设备不存在"),
    USER_RELATION_DEVICE_NOT_EXIST(65002, "用户设备关联不存在"),
    DEVICE_BIND_FAIL(65003, "设备绑定失败"),
    INVALID_QRCODE(65004, "无效的二维码"),
    QRCODE_EXPIRED(65005, "二维码已失效"),
    SHARE_NOT_EXISTS(65006, "设备分享不存在"),
    DEVICE_UNBIND_FAIL(65007, "设备解绑失败"),
    DEVICE_ALREADY_BINDED(65008, "设备已经被绑定"),
    CANNOT_SHARE_SELF(65009, "设备不能分享给自己"),
    DEVICE_NOT_YOURS(65009, "设备的主人不是你"),
    SN_ALIAS_EMPTY(65010, "SN和别名不能同时为空"),
    SN_LENGTH_ERROR(65011, "SN长度最大为64"),
    ALIAS_LENGTH_ERROR(65012, "别名长度最大为200"),
    DEVICE_INFO_UPDATE_FAIL(65013, "设备信息修改失败"),
    DEVICE_OLD_OWNER_UNBIND_FAIL(65014, "设备原始主人解除失败"),
    GIZWITS_DEVICE_UNBIND_FAIL(65015, "机智云设备解绑失败did"),
    GIZWITS_DEVICE_BIND_FAIL(65015, "机智云设备绑定失败did"),
    MODEL_NOT_EXISTS(65016, "型号不存在"),
    CANNOT_REPEAT_SHARE(65017, "不能重复分享"),

    //-------------------智能用电相关------------------//
    SMART_ELECTRICITY_NOT_ENABLE(66001,"未开启智能用电"),
    SMART_ELECTRICITY_ENABLED(66002,"智能用电已经开启"),
    SMART_ELECTRICITY_ENABLE_FAIL(66003,"智能用电开启失败"),
    SMART_ELECTRICITY_DISENABLE_FAIL(66004,"智能用电关闭失败"),
    SMART_ELECTRICITY_NOT_SUPPORT(66005,"该设备不支持开启智能用电"),

    //-------------------app波峰波谷相关------------------//
    PEAKVALLEY_ALEARY_EXIST(67001,"波峰波谷表已存在"),
    PEAKVALLEY_NOT_EXIST(67002,"波峰波谷表不存在"),
    PEAKVALLEY_TIMEAREA_ILLEGAL(67003,"传入时间不合法"),
    PEAKVALLEY_GET_DID_FAIL(67004,"根据设备主键获取设备DID失败"),
    PEAKVALLEY_PARSE_FAIL(67005,"传入参数解析异常"),
    PEAKVALLEY_TIME_AREA_CONFLICT(67008,"传入的时间段有冲突"),

    //-------------------app睡眠时间表------------------//
    SLEEPDIY_INPUT_ILLEGAL(68001,"睡眠表传入参数不合法"),
    SLEEPDIY_TIMEAREA_CONFLICT(68002,"睡眠表传入时间段冲突"),
    SLEEPDIY_TIMEAREA_ILLEGAL(68003,"睡眠表传入时间大于十二小时"),
    SLEEPDIY_UNKNOWN_DEVICE(68004,"未知的设备"),

    //-------------------app端型号信息相关------------------//
    DEVICE_MODEL_INFO_ILLEGAL(69001,"设备型号传入参数不合法"),
    DEVICE_MODEL_COUNT_ILLEGAL(69002,"设备型号返回多于一条"),
    //-------------------定时任务相关------------------//
    SCHEDULE_CREATE_FAIL(70001,"定时任务创建失败"),
    SCHEDULE_NOT_FOUND(70002,"定时任务不存在"),
    SCHEDULE_TURN_ON_FAIL(70003,"定时任务开启失败"),
    SCHEDULE_TURN_OFF_FAIL(70004,"定时任务关闭失败"),
    SCHEDULE_DELETE_FAIL(70005,"定时任务删除失败"),
    //-------------------用电曲线相关------------------//
    ELECTRIC_DATA_SAVED(71001,"用电数据已经保存"),

    ;
    // 其他未知错误
    public static final int OTHER = -999999;


    private int code;
    private String msg;

    AppCode(int code, String msg) {
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
