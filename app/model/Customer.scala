package model

import reactivemongo.api.MongoDriver
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.bson.BSONDocument
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Await

case class Customer(username:String,password:String,name:String,email:String,mobile:String)

case class Login(username:String,password:String)

class CustomerService {

  val driver = new MongoDriver
  val connection = driver.connection(List("localhost"))
  val database = Await.result(connection.database("sonu"),10.seconds)
  val collection= database.collection[BSONCollection]("customer")


  def insert(username:String,password:String,name:String,email:String,mobile:String):String ={
    try {
      val document = BSONDocument("username" -> username, "password" -> password, "name" -> name,
        "email" -> email, "mobile" -> mobile)
      Await.result(collection.insert(document), 10.seconds)
      "Inserted"
    }
    catch {
      case error:Exception => "Duplicate"
    }
  }

  def find(username:String,password:String):Boolean={
    try {
      val document = BSONDocument("username" -> username,"password" -> password)
      val result = Await.result(collection.find(document).one[BSONDocument], 10.seconds)
      if(result.isDefined) true else false
    }
    catch {
      case error:Exception => false
    }
  }
}

object CustomerService extends CustomerService

