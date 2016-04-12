package model

import org.scalatest.FunSuite

/**
  * Created by knoldus on 11/4/16.
  */
class CustomerServiceSpec extends FunSuite{

  val customerService = model.CustomerService

  test("One should be able to insert the document"){

    val result = customerService.insert("akankshac","akanksha","Akanksha Chabbra","akanksha@gmail,com","9834773722")
    assert(result==="Inserted")
  }

  test("One should not be able to insert the duplicate document"){

    val result = customerService.insert("akankshac","akanksha","Akanksha Chabbra","akanksha@gmail,com","9834773722")
    assert(result==="Duplicate")
  }

  test("One should be able to login with correct details"){

    val result = customerService.find("akankshac","akanksha")
    assert(result===true)
  }

  test("One should not be able to login with incorrect details"){

    val result = customerService.find("akankshac","akanksha123")
    assert(result===false)
  }



}
