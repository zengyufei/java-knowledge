package Z_0019_Netty入门IO通讯.S6_NettyByteBuf实验;

import cn.hutool.core.lang.Console;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/**
 * 容量 API
 * capacity()
 *
 * 表示 ByteBuf 底层占用了多少字节的内存（包括丢弃的字节、可读字节、可写字节），不同的底层实现机制有不同的计算方式，后面我们讲 ByteBuf 的分类的时候会讲到
 *
 * maxCapacity()
 *
 * 表示 ByteBuf 底层最大能够占用多少字节的内存，当向 ByteBuf 中写数据的时候，如果发现容量不足，则进行扩容，直到扩容到 maxCapacity，超过这个数，就抛异常
 *
 * readableBytes() 与 isReadable()
 *
 * readableBytes() 表示 ByteBuf 当前可读的字节数，它的值等于 writerIndex-readerIndex，如果两者相等，则不可读，isReadable() 方法返回 false
 *
 * writableBytes()、 isWritable() 与 maxWritableBytes()
 *
 * writableBytes() 表示 ByteBuf 当前可写的字节数，它的值等于 capacity-writerIndex，如果两者相等，则表示不可写，isWritable() 返回 false，但是这个时候，并不代表不能往 ByteBuf 中写数据了， 如果发现往 ByteBuf 中写数据写不进去的话，Netty 会自动扩容 ByteBuf，直到扩容到底层的内存大小为 maxCapacity，而 maxWritableBytes() 就表示可写的最大字节数，它的值等于 maxCapacity-writerIndex
 *
 * 读写指针相关的 API
 * readerIndex() 与 readerIndex(int)
 *
 * 前者表示返回当前的读指针 readerIndex, 后者表示设置读指针
 *
 * writeIndex() 与 writeIndex(int)
 *
 * 前者表示返回当前的写指针 writerIndex, 后者表示设置写指针
 *
 * markReaderIndex() 与 resetReaderIndex()
 *
 * 前者表示把当前的读指针保存起来，后者表示把当前的读指针恢复到之前保存的值，下面两段代码是等价的
 *
 * // 代码片段1
 * int readerIndex = buffer.readerIndex();
 * // .. 其他操作
 * buffer.readerIndex(readerIndex);
 *
 *
 * // 代码片段二
 * buffer.markReaderIndex();
 * // .. 其他操作
 * buffer.resetReaderIndex();
 * 希望大家多多使用代码片段二这种方式，不需要自己定义变量，无论 buffer 当作参数传递到哪里，调用 resetReaderIndex() 都可以恢复到之前的状态，在解析自定义协议的数据包的时候非常常见，推荐大家使用这一对 API
 *
 * markWriterIndex() 与 resetWriterIndex()
 *
 * 这一对 API 的作用与上述一对 API 类似，这里不再 赘述
 *
 * 读写 API
 * 本质上，关于 ByteBuf 的读写都可以看作从指针开始的地方开始读写数据
 *
 * writeBytes(byte[] src) 与 buffer.readBytes(byte[] dst)
 *
 * writeBytes() 表示把字节数组 src 里面的数据全部写到 ByteBuf，而 readBytes() 指的是把 ByteBuf 里面的数据全部读取到 dst，这里 dst 字节数组的大小通常等于 readableBytes()，而 src 字节数组大小的长度通常小于等于 writableBytes()
 *
 * writeByte(byte b) 与 buffer.readByte()
 *
 * writeByte() 表示往 ByteBuf 中写一个字节，而 buffer.readByte() 表示从 ByteBuf 中读取一个字节，类似的 API 还有 writeBoolean()、writeChar()、writeShort()、writeInt()、writeLong()、writeFloat()、writeDouble() 与 readBoolean()、readChar()、readShort()、readInt()、readLong()、readFloat()、readDouble() 这里就不一一赘述了，相信读者应该很容易理解这些 API
 *
 * 与读写 API 类似的 API 还有 getBytes、getByte() 与 setBytes()、setByte() 系列，唯一的区别就是 get/set 不会改变读写指针，而 read/write 会改变读写指针，这点在解析数据的时候千万要注意
 *
 * release() 与 retain()
 *
 * 由于 Netty 使用了堆外内存，而堆外内存是不被 jvm 直接管理的，也就是说申请到的内存无法被垃圾回收器直接回收，所以需要我们手动回收。有点类似于c语言里面，申请到的内存必须手工释放，否则会造成内存泄漏。
 *
 * Netty 的 ByteBuf 是通过引用计数的方式管理的，如果一个 ByteBuf 没有地方被引用到，需要回收底层内存。默认情况下，当创建完一个 ByteBuf，它的引用为1，然后每次调用 retain() 方法， 它的引用就加一， release() 方法原理是将引用计数减一，减完之后如果发现引用计数为0，则直接回收 ByteBuf 底层的内存。
 *
 * slice()、duplicate()、copy()
 *
 * 这三个方法通常情况会放到一起比较，这三者的返回值都是一个新的 ByteBuf 对象
 *
 * slice() 方法从原始 ByteBuf 中截取一段，这段数据是从 readerIndex 到 writeIndex，同时，返回的新的 ByteBuf 的最大容量 maxCapacity 为原始 ByteBuf 的 readableBytes()
 * duplicate() 方法把整个 ByteBuf 都截取出来，包括所有的数据，指针信息
 * slice() 方法与 duplicate() 方法的相同点是：底层内存以及引用计数与原始的 ByteBuf 共享，也就是说经过 slice() 或者 duplicate() 返回的 ByteBuf 调用 write 系列方法都会影响到 原始的 ByteBuf，但是它们都维持着与原始 ByteBuf 相同的内存引用计数和不同的读写指针
 * slice() 方法与 duplicate() 不同点就是：slice() 只截取从 readerIndex 到 writerIndex 之间的数据，它返回的 ByteBuf 的最大容量被限制到 原始 ByteBuf 的 readableBytes(), 而 duplicate() 是把整个 ByteBuf 都与原始的 ByteBuf 共享
 * slice() 方法与 duplicate() 方法不会拷贝数据，它们只是通过改变读写指针来改变读写的行为，而最后一个方法 copy() 会直接从原始的 ByteBuf 中拷贝所有的信息，包括读写指针以及底层对应的数据，因此，往 copy() 返回的 ByteBuf 中写数据不会影响到原始的 ByteBuf
 * slice() 和 duplicate() 不会改变 ByteBuf 的引用计数，所以原始的 ByteBuf 调用 release() 之后发现引用计数为零，就开始释放内存，调用这两个方法返回的 ByteBuf 也会被释放，这个时候如果再对它们进行读写，就会报错。因此，我们可以通过调用一次 retain() 方法 来增加引用，表示它们对应的底层的内存多了一次引用，引用计数为2，在释放内存的时候，需要调用两次 release() 方法，将引用计数降到零，才会释放内存
 * 这三个方法均维护着自己的读写指针，与原始的 ByteBuf 的读写指针无关，相互之间不受影响
 * retainedSlice() 与 retainedDuplicate()
 *
 * 相信读者应该已经猜到这两个 API 的作用了，它们的作用是在截取内存片段的同时，增加内存的引用计数，分别与下面两段代码等价
 *
 * // retainedSlice 等价于
 * slice().retain();
 *
 * // retainedDuplicate() 等价于
 * duplicate().retain()
 * 使用到 slice 和 duplicate 方法的时候，千万要理清内存共享，引用计数共享，读写指针不共享几个概念，下面举两个常见的易犯错的例子
 *
 * 多次释放
 * Buffer buffer = xxx;
 * doWith(buffer);
 * // 一次释放
 * buffer.release();
 *
 *
 * public void doWith(Bytebuf buffer) {
 * // ...
 *
 * // 没有增加引用计数
 * Buffer slice = buffer.slice();
 *
 * foo(slice);
 *
 * }
 *
 *
 * public void foo(ByteBuf buffer) {
 * // read from buffer
 *
 * // 重复释放
 * buffer.release();
 * }
 * 这里的 doWith 有的时候是用户自定义的方法，有的时候是 Netty 的回调方法，比如 channelRead() 等等
 *
 * 不释放造成内存泄漏
 * Buffer buffer = xxx;
 * doWith(buffer);
 * // 引用计数为2，调用 release 方法之后，引用计数为1，无法释放内存
 * buffer.release();
 *
 *
 * public void doWith(Bytebuf buffer) {
 * // ...
 *
 * // 增加引用计数
 * Buffer slice = buffer.retainedSlice();
 *
 * foo(slice);
 *
 * // 没有调用 release
 *
 * }
 *
 *
 * public void foo(ByteBuf buffer) {
 * // read from buffer
 * }
 * 想要避免以上两种情况发生，大家只需要记得一点，在一个函数体里面，只要增加了引用计数（包括 ByteBuf 的创建和手动调用 retain() 方法），就必须调用 release() 方法
 * @author zengyufei
 */
public class U1_ByteBuf实验 {
    
    public static void main(String[] args) {
        // 9::capacity  100::max-capacity
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(9, 100);
        print("allocate ByteBuf", buffer);
        
        // 往 byteBuf 填充 4 个字节，此时写指针在 4，未到 capacity，直接写不需要扩容。
        buffer.writeBytes(new byte[]{1, 2, 3, 4});
        print("writeBytes(1,2,3,4)", buffer);
        
        // 往 byteBuf 填充 int 4 字节，此时写指针在 8，未到 capacity，直接写不需要扩容。
        buffer.writeInt(12);
        print("writeInt(12)", buffer);
        
        // 往 byteBuf 填充 int 1 字节，此时写指针在 9，已经与 capacity 相等。
        // 不可写。
        buffer.writeBytes(new byte[]{5});
        print("writeBytes(new byte[]{5})", buffer);
        
        // 往 byteBuf 填充 int 1 字节，检查 capacity == max-capacity，如果不相等，则扩容。
        // 扩容后再写。capacity 也改变。
        buffer.writeBytes(new byte[]{6});
        print("writeBytes(6)", buffer);
        
        // get 方法不改变读写指针
        Console.log("getByte(3) return {}", buffer.getByte(3));
        Console.log("getShort(3) return {}", buffer.getShort(3));
        Console.log("getInt(3) return {}", buffer.getInt(3));
        print("getByte() 方法不改变指针", buffer);
        
        // set 方法不改变读写指针
        Console.log("readableBytes() return {}", buffer.readableBytes());
        buffer.setByte(buffer.readableBytes() + 1, 0);
        print("setByte() 方法不改变指针", buffer);
        
        // read 方法改变 读指针
        byte[] readByte = new byte[buffer.readableBytes()];
        buffer.readBytes(readByte);
        print("readByte(" + readByte.length + ")", buffer);
        
    }
    
    private static void print(String action, ByteBuf buffer) {
        Console.log("分割线 ==========={}============", action);
        Console.log("实际占用字节数(丢弃的字节、可读字节、可写字节) capacity(): {}", buffer.capacity());
        Console.log("最大长度 maxCapacity(): {}", buffer.maxCapacity());
        Console.log("读指针位置 readerIndex(): {}", buffer.readerIndex());
        Console.log("当前可读的字节数 readableBytes(): {}", buffer.readableBytes());
        Console.log("是否可读(readIndex < writerIndex) isReadable(): {}", buffer.isReadable() ? "是" : "否");
        Console.log("写指针位置 writerIndex(): {}", buffer.writerIndex());
        Console.log("当前可写的字节数(capacity - writerIndex) writableBytes(): {}", buffer.writableBytes());
        Console.log("是否可写(writerIndex < capacity) isWritable(): {}", buffer.isWritable() ? "是" : "否");
        Console.log("最大可写字节数(maxCapacity - writerIndex) maxWritableBytes(): {}", buffer.maxWritableBytes());
        System.out.println();
    }
    
}
