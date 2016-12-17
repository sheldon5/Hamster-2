package cn.coselding.hamster.dto;

import cn.coselding.hamster.enums.Status;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 全局统一的消息返回格式
 * Created by linyuqiang on 16/11/25.
 */
public class CommonMessage<T> {
    //返回值
    private Status status;
    //统计结果
    private T result;

    private static List NULL = Collections.emptyList();

    public CommonMessage(T result, Status status) {
        this.result = result;
        this.status = status;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public static CommonMessage serverError() {
        return new CommonMessage(NULL, Status.SERVER_ERROR);
    }

    public static <T> CommonMessage<T> success(T res) {
        return new CommonMessage(res, Status.SUCCESS);
    }

    public static CommonMessage<String> illegalParams(String msg) {
        return new CommonMessage(msg, Status.ILLEGAL_PARAMS);
    }

    public static CommonMessage beyongQPMThreshold() {
        return new CommonMessage(NULL, Status.BEYOND_QPM_THRESHOLD);
    }

    @Override
    public String toString() {
        return "CommonMessage{" +
                "status=" + status +
                ", result=" + result +
                '}';
    }
}
