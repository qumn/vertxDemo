package xyz.qumn.learn01

import io.vertx.core.AbstractVerticle
import io.vertx.core.Vertx
import io.vertx.kotlin.coroutines.CoroutineVerticle
import io.vertx.kotlin.coroutines.await
import kotlinx.coroutines.delay

fun main() {
  val vertx = Vertx.vertx()
  //vertx.deployVerticle(ShareDate1())
  //vertx.deployVerticle(ShareDate2())
  vertx.deployVerticle(ShareDateAsync1())
  vertx.deployVerticle(ShareDateAsync2())
}

class ShareDateAsync1 : CoroutineVerticle() {
  override suspend fun start() {
    val sharedData = vertx.sharedData()
    val map = sharedData.getAsyncMap<String, String>("map1").await()
    map.put("foo", "bar").await()
    println("put data")
  }
}

class ShareDateAsync2 : CoroutineVerticle() {
  override suspend fun start() {
    val sharedData = vertx.sharedData()
    val map = sharedData.getAsyncMap<String, String>("map1").await() // 并不会等到有数据被放入, 如果是等到有数据被放入，最好的方法是使用eventbus
    val value = map.get("foo").await()
    println(value)
  }
}

class ShareDate1 : AbstractVerticle() {
  override fun start() {
    val sharedData = vertx.sharedData()
    println(Thread.currentThread().name + " put data")
    val map1 = sharedData.getLocalMap<String, String>("map1")
    map1["foo"] = "boo"
    val map2 = sharedData.getLocalMap<String, String>("map2")
    map2["eek"] = "123"
    val eventBus = vertx.eventBus()
    eventBus.publish("consumer", "had put date")
  }
}

class ShareDate2 : AbstractVerticle() {
  override fun start() {
    val sharedData = vertx.sharedData()
    println(Thread.currentThread().name + " get data")
    val map1 = sharedData.getLocalMap<String, String>("map1")
    val map2 = sharedData.getLocalMap<String, String>("map2")
    val eventBus = vertx.eventBus()
    eventBus.consumer<String>("consumer") {
      println("foo: " + map1["foo"])
      println("eek: " + map2["eek"])
    }
  }
}

