package socketChannelExample;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Client {

    public static void main(String[] args) {
        try {
            SocketChannel socketChannel = SocketChannel.open();
            // connect returns immediately in non-blocking modes
            socketChannel.configureBlocking(false);

            socketChannel.connect(new InetSocketAddress(8080));

            while (!socketChannel.finishConnect()) {

            }

            ByteBuffer buf = ByteBuffer.allocate(48);
            buf.clear();

            int bytesRead = socketChannel.read(buf);
            buf.flip();

            while (buf.hasRemaining()) {
                System.out.print((char) buf.get());
            }

            socketChannel.close();

        } catch (IOException e) {

        }

    }

}
