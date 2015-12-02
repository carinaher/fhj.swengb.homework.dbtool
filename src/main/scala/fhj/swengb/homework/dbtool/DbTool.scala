package fhj.swengb.homework.dbtool

import java.awt.Button
import java.net.URL
import java.sql.{Connection, DriverManager, ResultSet, Statement}
import java.util.ResourceBundle
import javafx.application.Application
import javafx.fxml.{FXML, Initializable, FXMLLoader}
import javafx.scene.image.{Image, ImageView}
import javafx.scene.layout.{BorderPane, StackPane, AnchorPane}
import javafx.scene.{Scene, Parent}
import javafx.stage.Stage
import javafx.scene.control.Label

import fhj.swengb.Person._
import fhj.swengb.{Speakers, Person, Students}

import scala.util.Try
import scala.util.control.NonFatal

/**
  * Example to connect to a database.
  *
  * Initializes the database, inserts example data and reads it again.
  *
  */
object Db {

  /**
    * A marker interface for datastructures which should be persisted to a jdbc database.
    *
    * @tparam T the type to be persisted / loaded
    */
  trait DbEntity[T] {

    /**
      * Recreates the table this entity is stored in
      *
      * @param stmt
      * @return
      */
    def reTable(stmt: Statement): Int

    /**
      * Saves given type to the database.
      *
      * @param c
      * @param t
      * @return
      */
    def toDb(c: Connection)(t: T): Int

    /**
      * Given the resultset, it fetches its rows and converts them into instances of T
      *
      * @param rs
      * @return
      */
    def fromDb(rs: ResultSet): List[T]

    /**
      * Queries the database
      *
      * @param con
      * @param query
      * @return
      */
    def query(con: Connection)(query: String): ResultSet = {
      con.createStatement().executeQuery(query)
    }

    /**
      * Sql code necessary to execute a drop table on the backing sql table
      *
      * @return
      */
    def dropTableSql: String

    /**
      * sql code for creating the entity backing table
      */
    def createTableSql: String

    /**
      * sql code for inserting an entity.
      */
    def insertSql: String

  }

  lazy val maybeConnection: Try[Connection] =
    Try(DriverManager.getConnection("jdbc:sqlite::memory:"))

}

case class Employee(firstName: String) extends Db.DbEntity[Employee] {

  def reTable(stmt: Statement): Int = 0

  def toDb(c: Connection)(t: Employee): Int = 0

  def fromDb(rs: ResultSet): List[Employee] = List()

  def dropTableSql: String = ""

  def createTableSql: String = ""

  def insertSql: String = ""

}


object DbTool {

  def main(args: Array[String]) {
    Application.launch(classOf[DbTool], args: _*)
    for {con <- Db.maybeConnection
         _ = Person.reTable(con.createStatement())
         _ = Students.sortedStudents.map(toDb(con)(_))
         s <- Person.fromDb(queryAll(con))} {
      println(s)
    }
  }
}

class DbTool extends javafx.application.Application {

  val Fxml = "/fhj/swengb/homework/dbtool/dbtool.fxml"
  val Css = "fhj/swengb/homework/dbtool/dbtool.css"

  val loader = new FXMLLoader(getClass.getResource(Fxml))

  override def start(stage: Stage): Unit =
    try {
      stage.setTitle("DBTOOL")
      loader.load[Parent]() // side effect
      val scene = new Scene(loader.getRoot[Parent]) //loads the default scene
      stage.setScene(scene)
      stage.setResizable(false) //window cannot be rescaled
      stage.getScene.getStylesheets.add(Css)
      stage.show()

    } catch {
      case NonFatal(e) => e.printStackTrace()
    }

}

class DbToolController extends Initializable {
  @FXML var borderPane: BorderPane = _

  override def initialize(location: URL, resources: ResourceBundle): Unit = {

  }
}
