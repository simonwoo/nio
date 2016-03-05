package socketChannelExample;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Server {

    public static void main(String[] args) {
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            // accept returns immediately in non-blocking modes
            serverSocketChannel.configureBlocking(false);

            ServerSocket serverSocket = serverSocketChannel.socket();
            serverSocket.bind(new InetSocketAddress(8080));

            ByteBuffer buf = ByteBuffer.allocate(48);

            while (true) {
                SocketChannel socketChannel = serverSocketChannel.accept();
                if (socketChannel != null) {
                    System.out.println("client is coming!");
                    buf.clear();
                    String hello = "hello client!";
                    buf.put(hello.getBytes());
                    // switch a buffer from writing mode to reading modes
                    buf.flip();
                    socketChannel.write(buf);
                    System.out.println("send message to client");
                }
            }
        } catch (IOException e) {

        }

    }

}
