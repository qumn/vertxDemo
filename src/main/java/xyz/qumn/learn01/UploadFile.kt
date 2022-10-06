package xyz.qumn.learn01

import io.vertx.core.AbstractVerticle
import io.vertx.core.Vertx
import io.vertx.core.http.Cookie
import io.vertx.core.http.CookieSameSite

class UploadFile : AbstractVerticle() {
  override fun start() {
    var server = vertx.createHttpServer()
    server.requestHandler { request ->
      request.setExpectMultipart(true);
      request.uploadHandler { upload ->
        println("Got a file upload " + upload.name())
        request.response().addCookie(Cookie.cookie("hello", "world").setSameSite(CookieSameSite.LAX).setDomain("localhost:8081"))
        request.response().end("Received a file")
      }
    }
    server.listen(8081)
  }
}

fun main() {
  var vertx = Vertx.vertx()
  vertx.deployVerticle(UploadFile())
}
