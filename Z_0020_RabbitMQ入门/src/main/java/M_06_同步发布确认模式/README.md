## 同步发布确认模式

这是一种简单的确认方式，它是一种**同步确认发布**的方式，也就是发布一个消息之后只有它被确认发布，后续的消息才能继续传递。

`channel.waitForConfirmsOrDie(long timeout)` 方法用于等待确认。

一旦信息得到确认，该方法就会返回。

如果消息在超时时间内未得到确认，或者消息被黑屏（意味着由于某种原因，代理无法处理该消息），该方法将抛出一个异常。

异常处理通常包括记录错误信息和/或重试发送信息。

这种技术非常简单，但也有一个很大的缺点：它会**大大降低发布速度**，因为消息的确认会**阻止所有后续消息的发布**。

这种方法的吞吐量不会超过每秒几百条已发布信息。

不过，这对于某些应用来说已经足够了。

### 测试方法:

* 1 运行 `K1_生产者`
