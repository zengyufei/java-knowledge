https://juejin.im/book/5b4bc28bf265da0f60130116/section/5b4bc28b5188251b1f224ee5

那么 Netty 到底是何方神圣？ 用一句简单的话来说就是：Netty 封装了 JDK 的 NIO，让你用得更爽，你不用再写一大堆复杂的代码了。 用官方正式的话来说就是：Netty 是一个异步事件驱动的网络应用框架，用于快速开发可维护的高性能服务器和客户端。

下面是我总结的使用 Netty 不使用 JDK 原生 NIO 的原因

1. 使用 JDK 自带的 NIO 需要了解太多的概念，编程复杂，一不小心 bug 横飞
1. Netty 底层 IO 模型随意切换，而这一切只需要做微小的改动，改改参数，Netty 可以直接从 NIO 模型变身为 IO 模型
1. Netty 自带的拆包解包，异常检测等机制让你从 NIO 的繁重细节中脱离出来，让你只需要关心业务逻辑
1. Netty 解决了 JDK 的很多包括空轮询在内的 Bug
1. Netty 底层对线程，selector 做了很多细小的优化，精心设计的 reactor 线程模型做到非常高效的并发处理
1. 自带各种协议栈让你处理任何一种通用协议都几乎不用亲自动手
1. Netty 社区活跃，遇到问题随时邮件列表或者 issue
1. Netty 已经历各大 RPC 框架，消息中间件，分布式通信中间件线上的广泛验证，健壮性无比强大

首先，引入 Maven 依赖，本文后续 Netty 都是基于 4.1.6.Final 版本

```maven
<dependency>
    <groupId>io.netty</groupId>
    <artifactId>netty-all</artifactId>
    <version>4.1.6.Final</version>
</dependency>
```