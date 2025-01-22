```yml
server:  
  port: 8080  
  servlet:  
    context-path: /myapp  
  tomcat:  
    connection-timeout: 3000
```

```java
@GetMapping("/hello")  
public String hello(@RequestParam String name) {  
    try {  
        TimeUnit.SECONDS.sleep(5);  
    } catch (InterruptedException e) {  
        throw new RuntimeException(e);  
    }  
    return "Hello " + name;  
}
```
postman test
setting->general
![[Pasted image 20241214220517.png]]
http://localhost:8080/myapp/hello?name=frank
Error: Response timed out

sh:curl -v -X GET "http://localhost:8080/myapp/hello?name=124" --connect-timeout 3
```shell
C:\Users\xuwen>curl -v -X GET "http://localhost:8080/myapp/hello?name=124" --max-time 3
Note: Unnecessary use of -X or --request, GET is already inferred.
* Host localhost:8080 was resolved.
* IPv6: ::1
* IPv4: 127.0.0.1
*   Trying [::1]:8080...
* Connected to localhost (::1) port 8080
> GET /myapp/hello?name=124 HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/8.9.1
> Accept: */*
>
* Request completely sent off
* Operation timed out after 3016 milliseconds with 0 bytes received
* closing connection #0
curl: (28) Operation timed out after 3016 milliseconds with 0 bytes received
```