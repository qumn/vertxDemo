package xyz.qumn.learn01;

import io.netty.handler.logging.ByteBufFormat;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetServerOptions;

public class TcpServer extends AbstractVerticle {
  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new TcpServer());
  }
  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    NetServerOptions opt = new NetServerOptions().setLogActivity(true).setActivityLogDataFormat(ByteBufFormat.SIMPLE);
    NetServer netServer = vertx.createNetServer(opt);
    netServer.connectHandler(socket -> {
      System.out.println("local: " + socket.localAddress());
      System.out.println("remote: " + socket.remoteAddress());
      System.out.println("connected to client");
      socket.handler(buffer -> {
        String received = buffer.getString(0, buffer.length());
        String threadName = Thread.currentThread().getName();
        System.out.println("threadName = " + threadName);
        System.out.println("received: " + received);
        if (received.startsWith("quit")) {
          socket.closeHandler(e -> {
            System.out.println("socket closed");
          });
          socket.close();
        }
        socket.write(received);
      });
    });
    netServer.listen(4321, res -> {
      if (res.succeeded()) {
        System.out.println("succeeded listen on 4321");
      } else {
        System.out.println("fail listen on 4321");
      }
    });
    startPromise.complete();
  }
}
