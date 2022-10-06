package xyz.qumn.learn01;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;

public class HttpServerDemo extends AbstractVerticle {
  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new HttpServerDemo());
  }
  @Override
  public void start() throws Exception {
    HttpServerOptions opt = new HttpServerOptions().setLogActivity(true);
    HttpServer server = vertx.createHttpServer(opt);
    server.requestHandler(req -> { // 当request的body被读取之后，就会调用这个方法
      HttpMethod method = req.method();
      System.out.println("req.uri() = " + req.uri());
      System.out.println("req.absoluteURI() = " + req.absoluteURI());
      System.out.println(req.uri());
      req.handler(buffer -> { // 每当body的一部分到达，都会调用这个方法
        System.out.println("I have received a chunk of the body of length: " + buffer.length());
      });
      //req.bodyHandler(buffer -> { // 只有Body全部收到之后才会调用这个方法
      //  System.out.println(buffer);
      //});
      System.out.println("req.params() = " + req.params());

      req.response().end("hello world");

    });
    server.listen(8088);
  }
}
