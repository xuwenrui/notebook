Java 11 引入了一个新的HTTP客户端API，它是Java标准库的一部分，旨在提供一个更现代、高效且易于使用的HTTP请求发送工具，相比老旧的`java.net.HttpURLConnection`和Apache HttpClient，这个新的API提供了更加流畅的API设计和对HTTP/2的支持。下面是使用Java 11标准HTTP客户端进行基本HTTP请求的一个示例：

#### 发送GET请求
```java
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class Java11HttpClientExample {

    public static void main(String[] args) throws Exception {
        // 创建HttpClient实例
        HttpClient httpClient = HttpClient.newHttpClient();

        // 构建请求
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://api.example.com/data"))
                .header("Accept", "application/json")
                // 设置超时时间，这里是10秒
                .timeout(Duration.ofSeconds(10))
                .build();

        // 发送请求并处理响应
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // 打印响应状态码和响应体
        System.out.println("Status code: " + response.statusCode());
        System.out.println("Response body: " + response.body());
    }
}
```


#### 发送POST请求（带有JSON体）
```java
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.nio.charset.StandardCharsets;

public class PostRequestExample {

    public static void main(String[] args) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        
        String jsonBody = "{\"key\":\"value\"}";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://api.example.com/data"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody, StandardCharsets.UTF_8))
                .timeout(Duration.ofSeconds(10))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Status code: " + response.statusCode());
        System.out.println("Response body: " + response.body());
    }
}
```
