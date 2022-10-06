package xyz.qumn.learn01;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.WebSocket;

public class WebSocketClientDemo extends AbstractVerticle {
  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new WebSocketClientDemo());
  }

  @Override
  public void start() throws Exception {
    HttpClient client = vertx.createHttpClient();
    client.webSocket(8089, "localhost", "/ws", ar -> {
      if (ar.succeeded()) {
        WebSocket ws = ar.result();
        System.out.println("Connected");
        ws.textMessageHandler(message -> {
          System.out.println("received: " + message);
          ws.writeTextMessage("client received: " + message);
        });
      }
    });
  }
}
