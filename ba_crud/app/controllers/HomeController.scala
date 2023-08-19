package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.db.Database
import java.sql.Connection
import com.ba.UsuarioEntity
import scala.collection.JavaConverters._


@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents, db: Database) extends BaseController {

  def index() = Action { implicit request: Request[AnyContent] =>
    val usuarios: List[UsuarioEntity] = db.withConnection { implicit connection =>
      val query = "SELECT * FROM usuario"
      val resultSet = connection.createStatement().executeQuery(query)
      UsuarioEntity.fromResultSet(resultSet).asScala.toList
    }

    Ok(views.html.index(usuarios))
  }
}
