import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * Created by zonzie on 2018/4/8.
 */
public class PlainOioServer {

    public void server(int port) throws IOException {
        ServerSocket socket = new ServerSocket(port);
        try {
            for(;;) {
                final Socket client = socket.accept();
                System.out.println("Accepted connection from " + client);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OutputStream out;
                        try {
                            out = client.getOutputStream();
                            out.write("Hi!".getBytes(Charset.forName("UTF-8")));
                            out.flush();
                            client.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                client.close();
                            } catch (IOException e) {
                                //
                            }
                        }
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
