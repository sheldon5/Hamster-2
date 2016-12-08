# pushy

[![Build Status](https://travis-ci.org/relayrides/pushy.svg?branch=master)](https://travis-ci.org/relayrides/pushy)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.relayrides/pushy/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.relayrides/pushy)

### 这是我自己的翻译版本，[原文地址](https://github.com/relayrides/pushy/blob/master/README.md)。

Pushy 是一个发送 [APNs](https://developer.apple.com/library/ios/documentation/NetworkingInternet/Conceptual/RemoteNotificationsPG/Chapters/ApplePushService.html) (iOS, OS X, 或 Safari) 推送通知的Java类库。这是一个[Turo](https://turo.com/)创建和维护的项目。

Pushy 使用Apple的基于HTTP/2的APNs协议来推送通知。她区别于其他类库的主要特征在于 [完善的文档](http://relayrides.github.io/pushy/apidocs/0.8/)，异步任务操作和为了工业规模而设计；对于Pushy来说,维持多个和APNs网关服务器的并行连接发送大量的通知到多个不同的app("topics")，是很简单又高效的。

我们坚信Pushy已经是发送APNs推送通知的最好的Java应用程序工具，我们希望您能通过报告bug或pull代码请求来使这个项目越来越好。 如果你有任何关于使用Pushy的问题，请加入我们[Pushy邮件列表](https://groups.google.com/d/forum/pushy-apns) 或者查看我们的[wiki](https://github.com/relayrides/pushy/wiki)。谢谢！

## 使用Pushy

如果你用[Maven](http://maven.apache.org/)，你可以通过在你的项目中的pom.xml文件中添加以下的依赖声明来向项目中添加Pushy：

```xml
<dependency>
    <groupId>com.relayrides</groupId>
    <artifactId>pushy</artifactId>
    <version>0.8.1</version>
</dependency>
```

如果你不使用Maven(或者其他像Gradle一样能识别Maven依赖的工具), 你就需要[下载Pushy的jar文件](https://github.com/relayrides/pushy/releases/download/pushy-0.8.1/pushy-0.8.1.jar)并直接添加到你的项目中。你还需要确认Pushy运行时依赖的以下组件是否在classpath中：

- [netty 4.1.5](http://netty.io/)
- [gson 2.6](https://github.com/google/gson)
- [slf4j 1.7.6](http://www.slf4j.org/) (还需要绑定SLF4J的具体实现，具体详细说明请看下面的[logging](#logging)模块)
- `netty-tcnative` (1.1.33.Fork22以上版本) 或者 `alpn-boot`其中之一的组件。具体的讨论请看下面的[系统准备](#system-requirements)模块
  - [alpn-api](http://www.eclipse.org/jetty/documentation/current/alpn-chapter.html) 如果你选择native SSL provider (`alpn-api`已经包含在`alpn-boot`里面了)；请看[系统需求](#system-requirements)模块查看详细内容)

Pushy的运行和构建需要在Java7以及以上版本。

## 发送推送通知

在你开始使用Pushy之前，你需要先做一些准备工作，包括到Apple注册你的app并获得所需的证书。这个准备工作的详细说明请看苹果官方文档的[Provisioning and Development](https://developer.apple.com/library/ios/documentation/NetworkingInternet/Conceptual/RemoteNotificationsPG/Chapters/ProvisioningDevelopment.html#//apple_ref/doc/uid/TP40008194-CH104-SW1)模块。请注意这里有一些[警告](https://github.com/relayrides/pushy/wiki/Certificates)，特别是Mac OS X 10.11 (El Capitan)以下的版本。

当你的app注册完成并获得相应的证书之后, 要开始推送通知，你需要做的第一件事是使用Pushy创建一个[`ApnsClient`](http://relayrides.github.io/pushy/apidocs/0.8/com/relayrides/pushy/apns/ApnsClient.html)。客户端需要一个证书和一个私钥向APNs服务器请求认证。保存证书的最常用方法是使用一个私钥加密PKCS#12文件(如果在我写这篇文章的这个时间点你遵守Apple的相关说明和建议，你会使用一个.p12后缀名的文件存储证书):

```java
final ApnsClient apnsClient = new ApnsClientBuilder()
        .setClientCredentials(new File("/path/to/certificate.p12"), "p12-file-password")
        .build();
```

当你创建了一个client实例之后，你就可以连接上APNs网关服务器可。请注意，这个连接过程是异步的；client实例将会马上返回一个Future对象，但在你发送任何推送通知之前，你需要等待这个连接过程完成之后才能进行。请注意，这个[`Future`](http://netty.io/4.1/api/io/netty/util/concurrent/Future.html)对象是Netty框架中的，它是Java中的[`Future`](http://docs.oracle.com/javase/7/docs/api/java/util/concurrent/Future.html)接口的一个拓展，它允许调用者添加监听器或添加methods来检测`Future`的状态。

```java
final Future<Void> connectFuture = apnsClient.connect(ApnsClient.DEVELOPMENT_APNS_HOST);
connectFuture.await();
```

只要你的client实例完成了和APNs服务器的连接，你就可以发送推送通知了。不过在你进行[推送通知](http://relayrides.github.io/pushy/apidocs/0.8/com/relayrides/pushy/apns/ApnsPushNotification.html)的时候，需要提供这几个数据：目标设备的token，代表app签名的topic，和一个推送通知负载（消息内容）。

```java
final SimpleApnsPushNotification pushNotification;

{
    final ApnsPayloadBuilder payloadBuilder = new ApnsPayloadBuilder();
    payloadBuilder.setAlertBody("Example!");

    final String payload = payloadBuilder.buildWithDefaultMaximumLength();
    final String token = TokenUtil.sanitizeTokenString("<efc7492 bdbd8209>");

    pushNotification = new SimpleApnsPushNotification(token, "com.example.myApp", payload);
}
```

和连接服务器一样，发送推送通知也是一个异步过程。你会马上获取一个`Future`对象，但在你获知你的推送消息是被APNs网关服务器接受还是拒绝之前，还需要等待`Future`过程完成。

```java
final Future<PushNotificationResponse<SimpleApnsPushNotification>> sendNotificationFuture =
        apnsClient.sendNotification(pushNotification);
```

`Future`对象的执行结果会有以下三种情形：

1. APNs网关服务器接收推送通知，并尝试将消息投递到token对应的目标设备。
2. APNs网关服务器拒绝推送通知，并且这是一个永久的错误，您的推送通知将不会被重新投递推送。此外，APNs服务器会给token对应的设备标记一个最近失效时间的[时间戳](https://developer.apple.com/library/ios/documentation/NetworkingInternet/Conceptual/RemoteNotificationsPG/Chapters/APNsProviderAPI.html#//apple_ref/doc/uid/TP40008194-CH101-SW18)。如果发生这个情况，你应该停止尝试向这个token对应的设备发送任何通知， 除非这个token在这个时间戳之后又重新上线了。
3. `Future`对象因为一些未知异常而执行失败，这通常是在某种特定情况下的暂时性的失败，调用者应该在问题解决之后对这个推送通知进行重新地投递发送。但如果是在[`ClientNotConnectedException`](http://relayrides.github.io/pushy/apidocs/0.8/com/relayrides/pushy/apns/ClientNotConnectedException.html)这种情况下投递失败，调用者应该通过调用阻塞方法[`ApnsClient#getReconnectionFuture()`](http://relayrides.github.io/pushy/apidocs/0.8/com/relayrides/pushy/apns/ApnsClient.html#getReconnectionFuture--)来返回一个`Future`对象，这个过程其实是在连接断开之后进行自动重连。

举个例子：

```java
try {
    final PushNotificationResponse<SimpleApnsPushNotification> pushNotificationResponse =
            sendNotificationFuture.get();

    if (pushNotificationResponse.isAccepted()) {
        System.out.println("Push notification accepted by APNs gateway.");
    } else {
        System.out.println("Notification rejected by the APNs gateway: " +
                pushNotificationResponse.getRejectionReason());

        if (pushNotificationResponse.getTokenInvalidationTimestamp() != null) {
            System.out.println("\t…and the token is invalid as of " +
                pushNotificationResponse.getTokenInvalidationTimestamp());
        }
    }
} catch (final ExecutionException e) {
    System.err.println("Failed to send push notification.");
    e.printStackTrace();

    if (e.getCause() instanceof ClientNotConnectedException) {
        System.out.println("Waiting for client to reconnect…");
        apnsClient.getReconnectionFuture().await();
        System.out.println("Reconnected.");
    }
}
```

再强调一遍，返回的这个`Future`对象是支持监听器的；在实际应用场景中，去等待对每个特定设备的阻塞推送，效率是极其低的。而在`Future`中添加一个监听器，在推送完成之后回调这个监听器的方法，和阻塞等待每个推送完成这种方式相比起来，显然能够提供更高效的高并发服务。

最后一点，在你的应用程序关闭的时候，你需要断开每个存活的client对象中的连接：

```java
final Future<Void> disconnectFuture = apnsClient.disconnect();
disconnectFuture.await();
```

client关闭连接的过程，也是一个异步过程。client会等待那些已经发送给APNs服务器的，但还没收到APNs服务器回复响应消息的推送通知，等这些消息收到了APNs的回复之后，再断开连接。调用者通常应该确保所有已经发送给APNs服务器的推送通知都得到服务器的回复之后，再关闭和服务器的连接。此外，在Pushy内部维护了一个消息队列，在你调用了`sendNotification`方法投放的推送通知对象都会先放到这个消息队列中，然后再一个个的发送给APNs服务器，也就是说，在你关闭client对象连接的瞬间，消息队列中如果还有未发送的消息存在，那这些消息就会在这个瞬间立即推送失败，client在这之后只负责等待未被服务器回复的消息，不再添加额外的推送通知。

## 系统准备

Pushy运行于Java 7以上版本，但在特定的运行环境中可能要添加额外的特定依赖。

APNs协议是基于[HTTP/2协议](https://http2.github.io/)的一套推送协议。HTTP/2是一套相对新的协议，以至于它的发展还没有延伸拓展到Java世界中。例如以下几点：

1. HTTP/2依赖于[ALPN](https://tools.ietf.org/html/rfc7301)，它是一种在TLS协议上拓展出来的协议协商机制。在目前还没有哪个Java版本原生支持ALPN协议。所以如果我们要用ALPN，目前可以在Java7或Java8中，使用[第三方的原生SSL provider](#using-a-native-ssl-provider)，或者使用[Jetty的ALPN实现](#using-jettys-alpn-implementation)。
2. HTTP/2还需要使用[ciphers](https://httpwg.github.io/specs/rfc7540.html#rfc.section.9.2.2)，这个直到Java8才被引入到JDK中。在Java7中最好是使用原生的SSL provider。但在Java8中，原生SSL provider不再是必备的了,但可能还是有一些性能上的不足。

通常来说，原生的SSL provider是满足HTTP/2对系统性能增强要求的最好方法，因为安装是相当简单的，并且它运行在Java 7以上版本通常能够提供比JDK的SSL provider更好的SSL执行性能。

### 使用一个原生SSL provider

在所有支持的Java版本中，通过netty-tcnative使用一个native SSL provider (比如[OpenSSL](https://www.openssl.org/), [BoringSSL](https://boringssl.googlesource.com/boringssl/), 或[LibreSSL](http://www.libressl.org/))，就能够满足HTTP/2对于ALPN和cipher套件的系统要求。为了使用native SSL provider, 你只需要去添加一个`netty-tcnative`依赖到你的项目中。`netty-tcnative`的wiki提供了[详细的说明](http://netty.io/wiki/forked-tomcat-native.html)，但简单来说，你需要额外添加一个和平台特性相关的依赖到你的项目中；我们推荐使用静态的原生链接——"uber jar"，它可以支持大多数的操作系统和CPU架构(比如`linux-x86_64`, `osx-x86_64`, 和`windows-x86_64`)。这个方式将会满足大多数在Java 7和Java 8下对HTTP/2的要求。

如果你需要添加netty-tcnative的"uber jar"包，你只需要添加以下依赖(前提是你使用Maven):

```xml
<dependency>
    <groupId>io.netty</groupId>
    <artifactId>netty-tcnative-boringssl-static</artifactId>
    <version>1.1.33.Fork22</version>
</dependency>
```

否则，你可能要根据你自己的选择添加相应的jar包到你的classpath中。

记得标注好Pushy是需要netty-tcnative 1.1.33.Fork22以上的版本。此外，你需要添加一个[`alpn-api`](http://mvnrepository.com/artifact/org.eclipse.jetty.alpn/alpn-api)作为你项目的`运行时`依赖。如果你手动地管理项目依赖，你只需要确保最新版的`alpn-api`在你的classpath中可用。

### 使用Jetty的ALPN实现

Jetty的ALPN实现，是native SSL provider的一种选择。请注明如果你不是使用JDK 8以上版本(或者你使用Oracle以外的JDK)，你需要查看是否分别达到cipher套件的要求，你可能要使用native SSL provider (也是满足ALPN的一种实现)，或使用另一种密码的provider (这部分内容在本文档中不涉及)。

使用Jetty的ALPN实现是比使用native SSL provider稍微有点复杂。你需要去选择一个和你当前使用的JDK版本匹配的`alpn-boot`版本，然后把它添加到你的*boot* classpath中(请注意这和常规的classpath*不*是同一个概念)。[详细说明](http://www.eclipse.org/jetty/documentation/current/alpn-chapter.html)在Jetty的官网中有。如果你选择使用`alpn-boot`的方式而不是用native SSL provider，我们强烈建议使用[`jetty-alpn-agent`](https://github.com/jetty-project/jetty-alpn-agent)，原因是它会自动选择和你使用的JRE相匹配的正确版本的`alpn-boot`。

## 指标

Pushy提供了一个监控指标的接口，赋予客户端对行为和性能的洞察力，你可以编写你自己的`ApnsClientMetricsListener`接口的实现来记录和报告指标。我们也提供了一个[使用了Dropwizard指标类库的指标监听器](https://github.com/relayrides/pushy/tree/master/dropwizard-metrics-listener)作为一个分离的模块。若想要开始监控接收指标数据，在你创建client对象的时候设置一个监听器即可：

```java
final ApnsClient apnsClient = new ApnsClientBuilder()
        .setClientCredentials(new File("/path/to/certificate.p12"), "p12-file-password")
        .setMetricsListener(new MyCustomMetricsListener())
        .build();
```

请注意，在你的监听器中实现的指标处理的method中，*绝不*能调用到阻塞代码。直接在handler方法中直接增加一个计数器是比较恰当的，但是调用数据库或者远程监控端点必须被转发到分离的其他线程当中。

## 使用代理

如果你需要使用一个代理通过国外服务器进行连接，你最好在你创建ApnsClient对象实例的时候，提供一个[`ProxyHandlerFactory`](http://relayrides.github.io/pushy/apidocs/0.8/com/relayrides/pushy/apns/proxy/ProxyHandlerFactory.html)对象。我们提供了HTTP，SOCKS4，和SOCKS5代理的`ProxyHandlerFactory`的具体实现。

举个例子:

```java
final ApnsClient apnsClient = new ApnsClientBuilder()
        .setClientCredentials(new File("/path/to/certificate.p12"), "p12-file-password")
        .setProxyHandlerFactory(new Socks5ProxyHandlerFactory(
            new InetSocketAddress("my.proxy.com", 1080), "username", "password"))
        .build();

final Future<Void> connectFuture = apnsClient.connect(ApnsClient.DEVELOPMENT_APNS_HOST);
connectFuture.await();
```

## 日志

Pushy使用[SLF4J](http://www.slf4j.org/)来记录日志。如果你还不熟悉它，SLF4J只提供了外观接口，允许用户在系统部署的时候添加一个专门的"绑定"到classpath中来选择要使用的具体日志类库。为了避免让你进行选择，Pushy*不*依赖任何SLF4J绑定；你需要添加一个你自己的绑定（要么添加到项目依赖中，或者直接安装绑定）。如果你没有在你的classpath中绑定SLF4J，你可能会看到如下的警告信息：

```
SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
SLF4J: Defaulting to no-operation (NOP) logger implementation
SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
```

更多详细信息，请看[SLF4J用户指南](http://www.slf4j.org/manual.html)。

Pushy使用的日记级别如下所示：

| 级别     | 事件日志                                                                        |
|-----------|---------------------------------------------------------------------------------------|
| `error`   | 严重、不可恢复的错误；如果是可恢复的错误出现这个级别的日志信息这说明Pushy有bug  |
| `warn`    | 严重但可恢复的错误；错误可能可以表明调用者的代码有一定的错误，并指定错误信息         |
| `info`    | 重要的生命周期事件                                                         |
| `debug`   |较小的生命周期事件；期待可能出现的异常信息                                       |
| `trace`   | 个别IO操作                                                                 |

## 在应用容器中使用Pushy

如果你计划在一个应用容器中使用Pushy(比如Tomcat)，你可能必须去进行一些额外的步骤，并且要知道一些具体的限制详情——["在应用容器中使用Pushy"的wiki页](https://github.com/relayrides/pushy/wiki/Using-Pushy-in-an-application-container)。

## 许可和状态

Pushy基于[MIT License](http://opensource.org/licenses/MIT)开源协议。

Pushy的当前最新版本为0.8.1。我们还在日臻完善它的功能（已经把它用在实际产品当中），在未来的1.0 发布版当中，对外提供的API和现在的版本相比，可能会有明显的差别。