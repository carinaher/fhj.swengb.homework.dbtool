package fhj.swengb.homework.dbtool

import java.sql.{Connection, DriverManager, ResultSet, Statement}

import fhj.swengb.homework.dbtool.db_table._

import scala.util.Try

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

  val employeeOne: Employee = Employee(1, "Erste", "Grad", 113,"06644545455")
  val employeeTwo: Employee = Employee(2, "Zweite", "Grad", 143,"0664445455455")
  val employeeThree: Employee = Employee(3, "Fritz", "Franz", 153,"06643454545455")
  val employeeFour: Employee = Employee(4, "Goti", "Nuts", 163,"06647879455")

  val employees: Set[Employee] = Set(employeeOne, employeeTwo, employeeThree, employeeFour)

  val websiteOne: Website = Website(1, "tester", 9848.54,"www.test.at")
  val websiteTwo: Website = Website(2, "cooleSeite", 14949.44,"www.sehrcool.at")
  val websiteThree: Website = Website(3, "checkThis", 4949.9,"www.checker.at")
  val websiteFour: Website = Website(4, "bhruda", 99848.54,"www.bistDuBhruda.at")

  val websites: Set[Website] = Set(websiteOne, websiteTwo, websiteThree, websiteFour)



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

    for {con <- Db.maybeConnection
         _ = Employee.reTable(con.createStatement())
         _ = employees.map(Employee.toDb(con)(_))
         e <- Employee.fromDb(Employee.queryAll(con))} {
      println(e)
    }

    for {con <- Db.maybeConnection
         _ = Website.reTable(con.createStatement())
         _ = websites.map(Website.toDb(con)(_))
         w <- Website.fromDb(Website.queryAll(con))} {
      println(w)
    }
  }


}
