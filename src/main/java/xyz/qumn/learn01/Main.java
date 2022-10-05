package xyz.qumn.learn01;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.file.FileSystem;
import io.vertx.core.file.OpenOptions;

public class Main extends AbstractVerticle {
  public static void main(String[] args) {
    Vertx.vertx().deployVerticle(new Main());
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    FileSystem fs = vertx.fileSystem();
    String path = "test.txt";
    fs.createFile(path)
      .compose(v -> fs.open(path, new OpenOptions().setWrite(true)))
      .compose(f -> {
          f.write(Buffer.buffer("hello world"));
          System.out.println("write to file");
          return Future.succeededFuture(f);
        }
      )
      .compose(f -> f.read(Buffer.buffer(), 0, 0, 16))
      .onSuccess(System.out::println);
  }
}
