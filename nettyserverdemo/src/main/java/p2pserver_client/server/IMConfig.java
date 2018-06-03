package p2pserver_client.server;

/**
 * @author zonzie
 * @date 2018/4/10 18:08
 */
public interface IMConfig {

    // 客户端配置

    // 版本号
    int CLIENT_VERSION = 1;

    // 服务器配置

    // 服务器ip
    String SERVER_HOST = "127.0.0.1";
    // 服务器端口号
    int SERVER_PORT = 9090;

    // 消息相关

    // 表示服务器消息
    int SERVER_ID = 0;
    // 即时通讯ID为1
    byte APP_IM = 1;
    // 连接后第一次消息确认链接和发送认证信息
    byte TYPE_CONNECT = 0;
    // 文本信息
    byte TYPE_MSG_TEXT = 1;
    // 空消息
    String MSG_EMPTY = "";
}
