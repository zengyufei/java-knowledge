## Qos 是指消费者能够接收但未消费的最大消息数量

### 本例设置

* F2_消费者 Qos 设置为 1
* F3_消费者 Qos 设置为 5
* F1_生产者 生成 0-5 共六次消息, 发送到mq
* 预期：F2_消费者能接收到 1条消息, F3_消费者能接收到 5条消息
* 测试结果符合预期

### 测试方法:

* 1 运行 `F4_运行`
