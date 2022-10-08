package xyz.qumn.learn01

import io.vertx.core.Vertx
import io.vertx.kotlin.coroutines.CoroutineVerticle
import io.vertx.kotlin.coroutines.await

suspend fun main() {
  var vertx = Vertx.vertx()
  var sharedData = vertx.sharedData()
  var map = sharedData.getAsyncMap<String, Int>("map1").await()
  map.put("number1", 0).await();
  var deployVerticle = vertx.deployVerticle(AsyncLock1())
  var deployVerticle1 = vertx.deployVerticle(AsyncLock2())
  deployVerticle.await();
  deployVerticle1.await();
  var value = map.get("number1").await()
  println("result: $value")

}

class AsyncLock1 : CoroutineVerticle(){
  override suspend fun start() {
    val sharedData = vertx.sharedData()
    val map = sharedData.getAsyncMap<String, Int>("map1").await()
    var i = 0;
    while(i++ < 100){
      val lock = sharedData.getLocalLock("lock1").await()
      map.put("number1", map.get("number1").await() + 1)
      println("lock1")
      vertx.setTimer(1000){
        lock.release()
      }
    }
    println(map.get("number1").await())
  }
}
class AsyncLock2 : CoroutineVerticle(){
  override suspend fun start() {
    val sharedData = vertx.sharedData()
    val map = sharedData.getAsyncMap<String, Int>("map1").await()
    var i = 0;
    while(i++ < 100){
      val lock = sharedData.getLocalLock("lock1").await()
      println("lock2")
      map.put("number1", map.get("number1").await() + 1)
      vertx.setTimer(1000){
        lock.release()
      }
    }
    println(map.get("number1").await())
  }
}
