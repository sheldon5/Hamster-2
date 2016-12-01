package cn.coselding.hamster.enums;

/**http返回状态
 * Created by linyuqiang on 16/11/25.
 */
public class Status {

    public static Status SUCCESS = new Status("0", "成功");
    public static Status UNKNOWN_FAIL = new Status("1", "未知错误");
    public static Status ILLEGAL_PARAMS = new Status("2", "参数错误");
    public static Status SERVER_ERROR = new Status("3", "服务端错误");
    public static Status EMPTY_FILE = new Status("4", "上传文件为空");
    public static Status AUTH_ERROR = new Status("5", "token错误");
    public static Status LOGIN_ERROR = new Status("6", "用户名或者密码错误");
    public static Status WRONG_TIME_PERIOD = new Status("7", "错误的时间跨度");
    public static Status TOO_LONG_PERIOD = new Status("8", "时间跨度太长");
    public static Status BEYOND_QPM_THRESHOLD = new Status("9", "接口调用太频繁,冻结调用");

    private String code;
    private String reason;

    Status(String code, String reason) {
        this.code = code;
        this.reason = reason;
    }

    public String getCode() {
        return code;
    }

    public String getReason() {
        return reason;
    }
}
