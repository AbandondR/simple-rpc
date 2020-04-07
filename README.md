#### a simple rpc
contain four modules, the description is as follows:
 + rpc-service : This is a common module for storing various service invocation interfaces
 + rpc-common :  common and ordinary object
 + rpc-client : rpc client, used to proxy business interface calls, send the calls to remote services through sockets, and parse the returned results
 + rpc-server : rpc server, responsible for receiving the caller's request, decoding, and calling the actual business object method.
 
 查找业务接口的实现（没有依赖于第三方框架）：
 ```text
通过配置文件service.properties进行查找。
以业务接口全限定名称作为key，具体实现类作为value，多个实现使用"，"分割
```
 