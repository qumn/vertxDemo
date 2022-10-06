package xyz.qumn.learn01;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.ServerWebSocket;

import java.util.Scanner;

public class WebSocketServerDemo extends AbstractVerticle {
  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new WebSocketServerDemo());
  }
  @Override
  public void start() throws Exception {
    HttpServer server = vertx.createHttpServer();
    server.requestHandler(request -> {
      if (request.path().equals("/ws")){
        Future<ServerWebSocket> fut = request.toWebSocket();
        fut.onSuccess(ws -> {
          ws.writeTextMessage("first message");
          ws.writeTextMessage("second message");
          try {
            Thread.sleep(1999);
          } catch (InterruptedException e) {
            throw new RuntimeException(e);
          }
          ws.textMessageHandler(message -> {
            System.out.println("server received: " + message);
          });
        });
      }else {

      }
    });
    server.listen(8089);

  }
}
