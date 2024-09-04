https://blog.csdn.net/oDeviloo/article/details/52918773

其实我们代码中的 Service 层的写法就可以看成是使用了外观模式。外观模式实现了子系统与客户端之间的松耦合关系，相当于 Controller 和 Dao 的解耦合，将负责的业务处理交给 Service，减少了客户端所需处理的对象数目，并使得子系统使用起来更加容易。