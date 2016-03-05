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

Select允许单一线程处理多个Channel。将Channel注册到Selector中，然后在select()方法上等待。该方法会一直阻塞直到有注册的Channel上的事件到达。该方法返回时，该线程开始处理事件，如连接，数据到达等。

![selects](http://7xq5i5.com1.z0.glb.clouddn.com/img_selectors.png)

## 细节
### Channel
### Buffer
buffer对应为一段内存块，你可以在buffer中写入数据，然后从buffer中读出数据。该内存块被封装为NIO Buffer Object，它包含一系列方法操纵该内存块。

#### 使用Buffer的基本步骤：
- 在Buffer中写入数据
- 调用buffer.flip()方法(将buffer从读模式切换到写模式)
- 从该Buffer中读出数据
- 调用buffer.clear()或buffer.compact()(clear清空buffer，compact仅仅清空你已经读取的数据，任何未读的数据都会被置于buffer头)

buffer有三个属性：
- capacity
- position
- limit

position和limit取决于buffer是处于读模式还是写模式。

![mode](http://7xq5i5.com1.z0.glb.clouddn.com/img_buffers_modes.png)

### Selector
selector在Java NIO中用于检查Channel的状态，并决定哪一个Channel用于读或写。它可以使单线程操纵多个Channel，这样你可以用更少的线程操纵更多的Channel。

#### 使用
1. 创建一个Selector

```java
Selector selector = Selector.open();
```

2. 注册Channel到Selector，Channel必须处于non-blocking模式。

```java
channel.configureBlocking(false);
SelectionKey key = channel.register(selector, SelectionKey.OP_READ, theObject);
```
3. 通过Selector选择Channel。

一个完整的例子：

```java
Selector selector = Selector.open();

channel.configureBlocking(false);

SelectionKey key = channel.register(selector, SelectionKey.OP_READ);


while(true) {
// select方法一直阻塞直到至少一个channel对你所注册的事件达到准备状态
  int readyChannels = selector.select();

  if(readyChannels == 0) continue;


  Set<SelectionKey> selectedKeys = selector.selectedKeys();

  Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

  while(keyIterator.hasNext()) {

    SelectionKey key = keyIterator.next();

    if(key.isAcceptable()) {
        // a connection was accepted by a ServerSocketChannel.

    } else if (key.isConnectable()) {
        // a connection was established with a remote server.

    } else if (key.isReadable()) {
        // a channel is ready for reading

    } else if (key.isWritable()) {
        // a channel is ready for writing
    }

    keyIterator.remove();
  }
}
```



