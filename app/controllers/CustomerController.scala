package controllers

import javax.inject._

import play.api.mvc._
import model.Customer
import model.Login
import play.api.data._
import play.api.data.Forms._
import play.api.Play.current
import play.api.i18n.Messages.Implicits._
import model.CustomerService

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class CustomerController @Inject()extends Controller{

  val customerForm = Form(
    mapping(
      "username" -> nonEmptyText,
      "password" -> nonEmptyText,
      "name" -> nonEmptyText,
      "email" -> nonEmptyText,
      "mobile" -> nonEmptyText
    )(Customer.apply)(Customer.unapply)
  )

  val loginForm = Form(
    mapping(
      "username" -> nonEmptyText,
      "password" -> nonEmptyText
    )(Login.apply)(Login.unapply)
  )

  def index:Action[AnyContent] = Action { implicit request =>
    Ok(views.html.index(customerForm))
  }

  def processRegistration:Action[AnyContent] = Action{
    implicit request =>
      customerForm.bindFromRequest.fold(
        badForm => Redirect("/").flashing("error" -> "You have not filled all the required fields"),
        validForm => {
         val result =  CustomerService.insert(validForm.username,validForm.password,validForm.name,validForm.email,
           validForm.mobile)
          result match {
            case "Inserted" =>  Redirect("/login").flashing("success" -> "You have been registered. Please Login")
            case "Duplicate" => Redirect("/").flashing("error" -> "The username already exists.")
          }
        }
      )
  }

  def showLogin:Action[AnyContent] = Action {
    implicit request =>
      Ok(views.html.login(loginForm))
  }

  def processLogin:Action[AnyContent] = Action{
    implicit request =>
      loginForm.bindFromRequest.fold(
        badForm => Redirect("/login").flashing("error" -> "You have not filled the required fields"),
        validForm => {
          val result =  CustomerService.find(validForm.username,validForm.password)
          result match {
            case true => Ok(views.html.home())
            case false => Redirect("/login").flashing("error" -> "Username or password is incorrect")
          }
        }
      )
  }

}
