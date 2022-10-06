package xyz.qumn.learn01;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientRequest;
import io.vertx.core.http.HttpClientResponse;
import io.vertx.core.http.HttpMethod;

public class HttpClientDemo extends AbstractVerticle {
  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new HttpClientDemo());
  }

  @Override
  public void start() throws Exception {
    HttpClient client = vertx.createHttpClient();
    client.request(HttpMethod.GET, 8088, "localhost", "/", ar1 -> {
      if (ar1.succeeded()) {
        HttpClientRequest request = ar1.result();
        request.send(ar -> {
          if (ar.succeeded()) {
            HttpClientResponse response = ar.result();
            response.bodyHandler(buffer -> {
              System.out.println("received: " + buffer);
            });
          } else {

          }
        });
      } else {
        System.out.println("failed connected to remote server");
      }
    });
  }
}
