package p2pserver_client.server;

import org.msgpack.annotation.Message;

/**
 * @author zonzie
 * @date 2018/4/10 18:14
 */
@Message
public class IMMessage {
    // 应用id
    private byte appId;
    // 版本号
    private int version;
    // 用户id
    private int uid;
    // 消息类型  0:登录  1:文字消息
    private byte msgType;
    // 接收方
    private int receiveId;
    // 消息内容
    private String msg;

    public IMMessage(byte appId, int version, int uid, byte msgType, int receiveId, String msg) {
        this.appId = appId;
        this.version = version;
        this.uid = uid;
        this.msgType = msgType;
        this.receiveId = receiveId;
        this.msg = msg;
    }

    public byte getAppId() {
        return appId;
    }

    public void setAppId(byte appId) {
        this.appId = appId;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public byte getMsgType() {
        return msgType;
    }

    public void setMsgType(byte msgType) {
        this.msgType = msgType;
    }

    public int getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(int receiveId) {
        this.receiveId = receiveId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("IMMessage{");
        sb.append("appId=").append(appId);
        sb.append(", version=").append(version);
        sb.append(", uid=").append(uid);
        sb.append(", msgType=").append(msgType);
        sb.append(", receiveId=").append(receiveId);
        sb.append(", msg='").append(msg).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
