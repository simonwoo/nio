package examples;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelExample {

	public static void main(String[] args) {

		RandomAccessFile aFile;
		try {
			aFile = new RandomAccessFile("/Users/wuchong/projects/test.txt",
					"rw");
		} catch (FileNotFoundException e) {
			throw new RuntimeException("File not found");
		}

		// read from channel in buffer
		FileChannel inChannel = aFile.getChannel();
		ByteBuffer buf = ByteBuffer.allocate(48);
		int bytesRead = read(inChannel, buf);

		while (bytesRead != -1) {

			System.out.println("Read " + bytesRead);
			buf.flip();

			while (buf.hasRemaining()) {
				System.out.print((char) buf.get());
			}

			buf.clear();
			bytesRead = read(inChannel, buf);
		}

		try {
			aFile.close();
		} catch (IOException e) {
			throw new RuntimeException("Can not close file");
		}
	}

	private static int read(FileChannel inChannel, ByteBuffer buf) {
		try {
			return inChannel.read(buf);
		} catch (IOException e) {
			throw new RuntimeException("Can not read data from file");
		}
	}
}
