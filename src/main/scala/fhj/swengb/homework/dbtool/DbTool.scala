package fhj.swengb.homework.dbtool

import java.sql.{Connection, DriverManager, ResultSet, Statement}

import fhj.swengb.homework.dbtool.db_table._

import javafx.fxml._
import javafx.scene._
import javafx.stage._

import scala.util.Try
import scala.util.control.NonFatal


/** MAL PROBIEREN :) **/

class dbTool extends javafx.application.Application {


  val Fxml = "/fhj/swengb/homework/dbtool/dbtool.fxml"
  val Css = "fhj/swengb/homework/dbtool/dbtool.css"

  val loader = new FXMLLoader(getClass.getResource(Fxml))

  override def start(stage: Stage): Unit =
    try {
      stage.setTitle("dbTool")
      val scene = new Scene(loader.getRoot[Parent])
      stage.setScene(scene)
      stage.getScene.getStylesheets.add(Css)
      stage.show()
    } catch {
      case NonFatal(e) => e.printStackTrace()
    }

}





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

  val userOne: User = User(1,"Angelo", "Merte", "0800666666")
  val userTwo: User = User(2,"Angela","Mertina", "0800777777")
  val userThree: User = User(3,"Angel", "Devil", "0800888888")
  val userFour: User = User(4,"Devil", "Angola", "0815")

  val users: Set[User] = Set(userOne, userTwo, userThree, userFour)

  val locationOne: Location = Location(1,"Scheißegalstraße", 66.66)
  val locationTwo: Location = Location(2,"Schleichdi", 77.77)
  val locationThree: Location = Location(3,"Ostblock", 88.88)
  val locationFour: Location = Location(4,"Dreckigweg", 99.99)

  val locations: Set[Location] = Set(locationOne, locationTwo, locationThree, locationFour)


  def main(args: Array[String]) {
    for {con <- Db.maybeConnection
         _ = User.reTable(con.createStatement())
         _ = users.map(User.toDb(con)(_))
         u <- User.fromDb(User.queryAll(con))} {
      println(u)
    }

    for {con <- Db.maybeConnection
         _ = Location.reTable(con.createStatement())
         _ = locations.map(Location.toDb(con)(_))
         l <- Location.fromDb(Location.queryAll(con))} {
      println(l)
    }
  }



}
