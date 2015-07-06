package controllers

import models.{CommentTable, ProfileTable}
import play.api.Play
import play.api.db.slick.{HasDatabaseConfig, DatabaseConfigProvider}
import play.api.mvc._
import play.api.libs.json._
import slick.driver.JdbcProfile


class Application extends Controller
  with ProfileTable
  with CommentTable
  with HasDatabaseConfig[JdbcProfile] {

  implicit val context = play.api.libs.concurrent.Execution.Implicits.defaultContext

  val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)

  import driver.api._

  def index = Action {
    Ok(views.html.index())
  }

  def comments = Action.async {
    db.run(c.result).map(res => Ok(Json.obj("data" -> res.toList)))
  }

  def profiles = Action.async {
    db.run(p.result).map(res => Ok(Json.obj("data" -> res.toList)))
  }
}

