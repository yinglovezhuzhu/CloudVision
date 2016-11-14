package com.vrcvp.cloudvision.bean;

import java.util.List;

/**
 * 讯飞语义返回数据实体类
 * Created by yinglovezhuzhu@gmail.com on 2016/11/10.
 */

public class XFSemanticResp {

    /** 响应码：操作成功 **/
    public static final int RC_SUCCESS = 0;
    /** 响应码：无效请求， 通常在用户提交的请求参数有误时返回。 错误信息在error 字段描述，具体参见“ 附录 I 系统级错误信息表” **/
    public static final int RC_BAD_REQUEST = 1;
    /** 响应码：服务器内部错误， 通常在服务器侧出现异常时返回。 错误信息在error 字段描述，具体参见“ 附录 I 系统级错误信息表” **/
    public static final int RC_SERVER_ERROR = 2;
    /** 响应码：业务操作失败， 语义解析服务出错。错误信息在 error 字段描述，具体参见业务相关的语义（ semantic）错误信息 **/
    public static final int RC_OPERATE_ERROR = 3;
    /** 响应码：服务不理解或不能处理该文本。文本没有匹配的服务场景 **/
    public static final int RC_NOT_MATCH = 4;

    private int rc; // 是 应答码
    private XFWebPage webPage; // 否 该字段提供了结果内容的HTML5页面，客户端可以无需解析处理直接展现
    private XFSemantic semantic; // 否 语义结构化表示，各服务自定义
    private XFAnswer answer; // 否 对结果内容的最简化文本/图片描述，各服务自定义
    private String operation; // 是 服务的细分操作编码，各业务服务自定义
    private String service; // 是 服务的全局唯一名称
    private XFSemanticData data; // 否 数据结构化表示，各服务自定义
    private String text; // 是 用户的输入，可能和请求中的原始text不完全一致，因服务器可能会对text进行语言纠错
    private XFError error; // 否 错误信息
    private List<XFSemanticResp> moreResults; // 否 在存在多个候选结果时，用于提供更多的结果描述
    private String history; // 否 上下文信息，客户端需要将该字段 结果传给下一次请求的history字段
    private XFTips tips; // 否 结果内容的关联信息，作为用户后续交互的引导展现(可能需要有 url 链接)

    public int getRc() {
        return rc;
    }

    public XFWebPage getWebPage() {
        return webPage;
    }

    public XFSemantic getSemantic() {
        return semantic;
    }

    public XFAnswer getAnswer() {
        return answer;
    }

    public String getOperation() {
        return operation;
    }

    public String getService() {
        return service;
    }

    public XFSemanticData getData() {
        return data;
    }

    public String getText() {
        return text;
    }

    public XFError getError() {
        return error;
    }

    public List<XFSemanticResp> getMoreResults() {
        return moreResults;
    }

    public String getHistory() {
        return history;
    }

    public XFTips getTips() {
        return tips;
    }

    @Override
    public String toString() {
        return "XFSemanticResp{" +
                "rc=" + rc +
                ", webPage=" + webPage +
                ", semantic=" + semantic +
                ", answer=" + answer +
                ", operation='" + operation + '\'' +
                ", service='" + service + '\'' +
                ", data='" + data + '\'' +
                ", text='" + text + '\'' +
                ", error=" + error +
                ", moreResults=" + moreResults +
                ", history='" + history + '\'' +
                ", tips=" + tips +
                '}';
    }
}
