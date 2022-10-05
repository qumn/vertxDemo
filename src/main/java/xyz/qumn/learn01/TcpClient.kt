package xyz.qumn.learn01

import io.vertx.core.AbstractVerticle
import io.vertx.core.net.NetClientOptions

class TcpClient : AbstractVerticle() {
  override fun start() {
    var opt = NetClientOptions().setConnectTimeout(10000).setLogActivity(true)
    var client = vertx.createNetClient(opt)
    client.connect(4321, "localhost"){
      if (it.succeeded()){
        println("succeed connected to remote server")
      }else{
        println("fail to connect to remote server")
      }
    }
  }
}
