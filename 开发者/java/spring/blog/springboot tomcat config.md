```yml
spring:
  application:
    name: springboot3-demo

server:
  port: 8080
  servlet:
    context-path: /myapp
  tomcat:
    threads:
      min-spare: 10
      max: 20
    max-connections: 30
    accept-count: 10
    connection-timeout: 20000
    background-processor-delay: 30
    max-http-post-size: 20971520
    max-swallow-size: 20971520
    max-http-form-post-size: 20971520
    max-http-header-size: 8192
    max-http-request-size: 20971520
    max-http-response-size: 20971520
    max-thread-idle-time: 60s
    protocol-header: X-Forwarded-Proto
    remote-ip-header: X-Forwarded-For
    internal-proxies: 192\.168\.\d{1,3}\.\d{1,3}
    basedir: ./logs
    access-log-enabled: true
    access-log-pattern: "%h %l %u %t \"%r\" %s %b"
    access-log-prefix: access_log
    access-log-suffix: .log
    access-log-buffered: false
    access-log-rotate: true
    access-log-file-date-format: yyyy-MM-dd


```

server.port: 服务器监听的端口号。
server.servlet.context-path: 应用的上下文路径。

server.tomcat.threads.min-spare: Tomcat 最小空闲线程数。确保始终有足够多的空闲线程来处理新的请求。
server.tomcat.threads.max: Tomcat 最大线程数。用于处理请求的最大线程数。

server.tomcat.max-connections: Tomcat 接受的最大连接数。超过这个数量的连接将被拒绝。
server.tomcat.accept-count: 当所有可能的请求处理线程都在使用时，传入连接请求的最大队列长度。超出此长度的连接将被拒绝。
server.tomcat.connection-timeout: 连接超时时间，单位为毫秒
server.tomcat.background-processor-delay: 后台处理器延迟时间，单位为秒。
server.tomcat.max-http-post-size: HTTP POST 请求的最大大小，单位为字节。
server.tomcat.max-swallow-size: Tomcat 可以吞咽的最大请求体大小，单位为字节。
server.tomcat.max-http-form-post-size: HTTP 表单 POST 请求的最大大小，单位为字节。
server.tomcat.max-http-header-size: HTTP 请求头的最大大小，单位为字节。
server.tomcat.max-http-request-size: HTTP 请求的最大大小，单位为字节。
server.tomcat.max-http-response-size: HTTP 响应的最大大小，单位为字节。
server.tomcat.max-thread-idle-time: 线程的最大空闲时间，单位为秒。
server.tomcat.protocol-header: 用于识别协议头的请求头名称。