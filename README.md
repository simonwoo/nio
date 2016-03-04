# Java NIO:
## 介绍
Java NIO主要包括三大组件：Channel, Buffer和Selector。

- Channels
  - FileChannel
  - DatagramChannel
  - SocketChannel
  - ServerSocketChannel
- Buffers
  - ByteBuffer
  - CharBuffer
  - DoubleBuffer
  - FloatBuffer
  - IntBuffer
  - LongBuffer
  - ShortBuffer
- Selectors

## 架构
在Java NIO中, 所有的IO都起始于Channel。数据可从Channel中读入到Buffer里，也可从Buffer里写入Channel中。

![channel-buffer](http://7xq5i5.com1.z0.glb.clouddn.com/img_channels_buffers.png)

Select允许单一线程处理多个Handle。将Channel注册到Selector中，然后在select()方法上等待。该方法会一直阻塞直到有注册的Channel上的事件到达。该方法返回时，该线程开始处理事件，如连接，数据到达等。

![selects](http://7xq5i5.com1.z0.glb.clouddn.com/img_selectors.png)



