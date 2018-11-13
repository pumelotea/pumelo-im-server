package io.pumelo.common.errorcode;

/**
 *
 * 基础知识普及
 *
 * HTTP状态码（HTTP Status Code）是用以表示网页伺服器HTTP响应状态的3位数字代码。
 * 它由 RFC 2616 规范定义的，并得到 RFC 2518、RFC 2817、RFC 2295、RFC 2774 与 RFC 4918 等规范扩展。
 * 所有状态码的第一个数字代表了响应的五种状态之一。
 * 1xx消息
 * 这一类型的状态码，代表请求已被接受，需要继续处理。这类响应是临时响应，只包含状态行和某些可选的响应头信息，并以空行结束
 *
 * 2xx成功
 * 这一类型的状态码，代表请求已成功被服务器接收、理解、并接受。
 *
 * 3xx重定向
 * 这类状态码代表需要客户端采取进一步的操作才能完成请求。通常，这些状态码用来重定向，后续的请求地址（重定向目标）在本次响应的Location域中指明。
 *
 * 4xx客户端错误
 * 这类的状态码代表了客户端看起来可能发生了错误，妨碍了服务器的处理
 *
 * 5xx服务器错误
 * 这类状态码代表了服务器在处理请求的过程中有错误或者异常状态发生
 *
 */
public enum Syscode implements BaseErrorCode {
    FAIL(-1,"操作失败！"),
    SC_OK(200,"REQUEST SUCCESS"),
    CREATE_UPDATE_SUCCESS(201,"OPERATE UPDATE SUCCESS"),
    DELETE_SUCCESS(204,"OPERATE DELETE SUCCESS"),
    CLIENT_BAD_REQUEST(400, "Bad Request!"),
    CLIENT_NOT_AUTHORIZATION(401, "没有权限"),
    SC_NOT_FOUND(404,"404 error, url path not found "),
    CLIENT_METHOD_NOT_ALLOWED(405, "请求方法不允许"),
    CLIENT_NO_ACCEPTABLE(406, "请求不接受"),
    SERVER_ERROR(500, "服务器异常"),

    SERVER_RUNNTIME_EXCEPTION(100000, "运行时异常"),
    SERVER_NULL_EXCEPTION(100001, "空值异常"),
    SERVER_CLASSCAST_EXCEPTION(100002, "[服务器]数据类型转换异常"),
    SERVER_IO_EXCEPTION(100003, "IO异常"),
    SERVER_NOSUCHMETHOD_EXCEPTION(100004, "未知方法异常"),
    SERVER_INDEXOUTOFBOUNDS_EXCEPTION(100005, "数组越界异常"),
    SERVER_NETWORK_EXCEPTION(100006, "网络异常"),
    SERVER_OPTIMISTIC_LOCKING_FAILURE(100007, "数据锁定失败,请刷新,获取最新数据"),
    SERVER_DATA_CHANGE(100008, "数据版本不一致,请刷新,获取最新数据"),


    /**前端请求的参数错误【一般数参数的格式错误，参数值错误】*/
    DTO_PARAMS_ERROR(60003,"请求参数错误"),
    /**后端没有查询到任何数据*/
    NOT_CONTENT(60004," DATA EMPTY"),
    /**验证码无效或过期*/
    INVALID_CAPTCHA(60005, "无效的验证码"),
    /**Access Token 无效*/
    JWT_TOKEN_INVALID(60006,"无效的accesstoken"),
    /**Access Token 过期*/
    JWT_TOKEN_EXPIRAT(60007,"accesstoken过期，请重新登陆"),
    /**重新登录*/
    LOGIN_AGAIN(60008,"长时间未操作，请重新登录"),
    /**用户名或密码错误*/
    ACCOUNT_PWD_ERROR(60009,"账号或密码错误"),
    SYSTEM_EXCEPTION(60010, "系统异常"),
    ISEXIST_ACCOUNT(60104,"登录账号已存在"),
    JWT_REFRESHTOKEN_INVALID(60105,"Invalid refreshtoken"),
    USER_SESSION_EXPIRAT(60106,"用户账号缓存过期"),
    ROLE_MENU_ISEXISTS(60107, "角色已经关联菜单，不能删除"),
    USER_IS_EXISTS(60108, "用户名已存在"),
    ROLE_TIED_ACCOUNT(60109, "角色已经分配账号"),
    ACCOUNT_NOT_EXISTS(60110, "账号不存在"),
    SUPPERROLE_ACOUNT_NOT_PERMISS_DEL(60111, "超级管理员不允许删除"),
    ROLE_NAME_NOT_NULL(60112, "角色名称不能为空"),
    ROLE_EXISTS(60113, "角色已存在"),
    ROLE_NOT_EXISTS(60114, "角色不存在"),
    PASSWORD_NOT_NULL(60115, "密码不能为空"),
    GENERAL_ROLE_PERMIT(60116, "只允许创建普通管理员"),
    ROLE_NOT_NULL(60117, "角色不能为空"),
    SUPERROLE_NOT_PERMIT_CHANGE(60118, "系统创建用户不允许改变角色"),
    ACCOUNT_EXISTS(60119, "账号已存在"),
    PASSWORD_NOT_SAME(60120, "2次密码输入不一致"),
    ACCOUNT_NOT_LOGINED(60121, "请线登录"),
    PASSWORD_MODIFY_FAIL(60122, "密码修改失败"),
    MAIL_EMPTY(60123, "邮箱不能为空"),
    FORGET_PASSWORD_TOKEN_NOT_EXISTS(60124, "密码重置token不存在或者过期了"),
    CAN_NOT_UPDATE_PERSONAL_ENABLE(60125, "不能修改用户自身的启用状态"),
    ACCOUNT_DISABLE(60126, "帐号被禁用"),
    ROLE_NOT_BELONG_TO_BRANCH(60127, "角色不属于该部门"),
    EXIST_ROLE_NOT_FOUND(60128, "部分角色信息未找到"),
    USER_ROLE_NOT_FOUND(60129,"用户角色信息获取失败"),
    USER_PHONE_IS_USING(60130,"手机号码正在被使用"),
    ADMIN_CAN_NOT_UPDATE(60131,"系统创建角色不能修改"),
    USER_NOT_EXIST(60132,"未找到帐号" ),
    CODE_UNCORRECT(60133,"CODE未通过" ),
    USER_NOT_ADMIN(60134,"非管理员帐号" ),
    CAN_NOT_UPDATE_SELF_ROLE_PERMISSION(60135,"不能修改自己角色的权限"),
    ROLE_USING(60136,"禁止删除正在使用的角色"),
    ADMIN_CAN_NOT_DELETE(60137,"禁止删除系统创建的角色"),
    CAN_NOT_UPDATE_SELF_USER_ROLE(60138,"自己不能修改自己的角色"),

    /**************************预分配相关*******************************/
    PREALLOT_NOT_FOUND(71001, "找不到预分配订单"),
    PREALLOT_DELIVERING(71002, "预分配订单已在处理中"),
    PREALLOT_NOT_DELIVER(71003, "预分配订单未在处理中"),
    PREALLOT_DELIVER(71004, "预分配订单已完成"),
    PREALLOT_NOT_YOURS(71005, "该预分配订单不是您处理的"),

    /**************************区域相关*******************************/
    REGION_NOT_FOUND(72001, "找不到区域"),
    /**************************点位相关*******************************/
    LOCATION_NOT_FOUND(73001, "找不到点位"),
    DEVICE_NOT_IN(73002, "设备不在点位中"),
    DEVICE_ALREADY_IN(73003, "设备已在点位中"),
    LOCATION_DEVICE_ASSOCIATION_NOT_FOUND(73004,"设备点位关联未找到" ),
    /**************************产品相关*******************************/
    PRODUCT_NOT_FOUND(74001, "找不到产品"),
    /**************************设备相关*******************************/
    DEVICE_NOT_FOUND(75001,"找不到设备"),
    MAC_HAS_BEEN_REGISTER(75002,"MAC已注册"),
    BOARD_ID_HAS_BEEN_REGISTER(75003,"主板ID已注册" ),
    ICCID_HAS_BEEN_REGISTER(75004,"ICCID已注册" ),
    DEVICE_HAS_ACCESSED(75005,"设备已加入系统" ),
    CANNOT_OPT_DEVICE(75006,"没有权限操作设备" ),
    DEVICE_NOT_TEST(75007,"设备未测试" ),
    DEVICE_NOT_PASS(75008,"设备未通过测试" ),
    DEVICE_NOT_ACCESS(75009,"设备未加入系统" ),
    DEVICE_NOT_ASSICUATE_WITH_COMPANY(75010,"设备与公司无关联" ),
    CANNOT_UPDATE_DEVICE_INFO(75011,"没有权限更改设备信息" ),
    /**************************商品相关*******************************/
    COMMODITY_TEMP_NOT_FOUND(79001,"找不到商品模板"),
    COMMODITY_LIB_NOT_FOUND(79002,"找不到相关商品库"),
    COMMODITY_PRICE_NOT_FOUND(79003,"找不到相关价格策略"),
    COMMODITY_TEMP_DELETE_FAILE(79004,"商品模板有关联数据,删除失败"),
    COMMODITY_PRICE_UPDATE_FAILE(79005,"价格策略已上架,修改失败"),
    /**************************公司相关*******************************/
    COMPANY_NAME_EXIST(20001,"公司名称已存在"),
    COMPANY_DOMAIN_EXIST(20002,"公司域名已存在"),
    COMPANY_NOT_EXIST(20003,"公司不存在"),
    BRANCH_NAME_EXIST(20004,"部门已存在"),
    BRANCH_CONTAINS_ROLES(20005,"该部门中已有角色存在"),
    COMPANY_HAS_CHILD(20006,"该公司下有子公司"),
    BRANCH_NOT_EXIST(20007,"部门不存在"),
    BRANCH_CONTAINS_ROLE(20008,"该部门存在该角色"),
    BRANCH_INFO_GET_FAILED(20009,"部门信息获取失败"),
    BRANCH_NOT_CONTAIN_ROLE(20010,"部门中不存在该角色"),
    TERMINAL_CAN_NOT_CREATE_COMPANY(20011,"终端无法向下创建公司"),
    APP_USERNAME_IS_USING(20012,"该APP帐号正在被使用"),
    APP_PHONE_IS_USING(20013,"手机号码正在被使用"),
    APPUSER_NOT_EXIST(20014,"APP帐号不存在"),
    APPUSER_NEED_ONE_ROLE_AT_LEAST(20015,"APP帐号至少需要担当一个角色"),

    /**************************交易相关*******************************/
    SMS_CODE_INCORRECT(80001, "验证码不正确"),
    ORDER_NOT_EXIST(80002, "订单不存在"),
    MEMBER_NOT_EXIST(80003, "会员不存在"), ;


    public static final int VALIDATE_EXCEPTION = -10000;
    // 其他未知错误
    public static final int OTHER = -999999;

    private int code;
    private String msg;

    Syscode(int code, String msg) {
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
        return "{"+"code:" + code +", msg:" + msg + "}";
    }
}
