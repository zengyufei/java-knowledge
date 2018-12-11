package Z_0019_Netty入门IO通讯.S6_NettyByteBuf实验;

import cn.hutool.core.lang.Console;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/**
 * 描述：
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
        print("writeBytes(5)", buffer);

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
        print("readByte("+readByte.length+")", buffer);

    }
    
    private static void print(String action, ByteBuf buffer) {
        Console.log("分割线 ==========={}============", action);
        Console.log("实际占用字节数(丢弃的字节、可读字节、可写字节) capacity(): {}", buffer.capacity());
        Console.log("最大长度 maxCapacity(): {}", buffer.maxCapacity());
        Console.log("读指针位置 readerIndex(): {}", buffer.readerIndex());
        Console.log("当前可读的字节数 readableBytes(): {}", buffer.readableBytes());
        Console.log("是否可读(readIndex < writerIndex) isReadable(): {}", buffer.isReadable()?"是":"否");
        Console.log("写指针位置 writerIndex(): {}", buffer.writerIndex());
        Console.log("当前可写的字节数(capacity - writerIndex) writableBytes(): {}", buffer.writableBytes());
        Console.log("是否可写(writerIndex < capacity) isWritable(): {}", buffer.isWritable()?"是":"否");
        Console.log("最大可写字节数(maxCapacity - writerIndex) maxWritableBytes(): {}", buffer.maxWritableBytes());
        System.out.println();
    }

}
