https://blog.csdn.net/odeviloo/article/details/53292643


介绍：提供一种方法顺序访问一个聚合对象中各个元素，而又不暴露该对象的内部表示。比如 java 中的 iterator。

场景：对于 Array、List、Map 我们一般都会进行迭代操作，如果我们将遍历的方法写到容器类中，会使得容器类显得比较臃肿。

就是 List Set 等遍历方式 iterator。