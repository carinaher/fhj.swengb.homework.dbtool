package fhj.swengb.homework.dbtool

import java.sql.{ResultSet, Connection, Statement}
import fhj.swengb.Db.DbEntity


import scala.collection.mutable.ListBuffer

/**
  * Created by Graf, Körner, Schneider, Karimova on 02.12.2015.
  */
object db_table {

  //Table Location

  //coordinates hashed in MD5, for security
  object Location extends DbEntity[Location]{
    val dropTableSql = "drop table if exists Location"
    val createTableSql = "create table Location (lid Integer, locationName String, coordinates Double)"
    val insertSql = "insert into Location (lid, locationName, coordinates) VALUES (?, ?, ?)"


    def reTable(stmt: Statement): Int = {
      stmt.executeUpdate(Location.dropTableSql)
      stmt.executeUpdate(Location.createTableSql)
    }

    def toDb(c: Connection)(l: Location): Int = {
      val pstmt = c.prepareStatement(insertSql)
      pstmt.setInt(1, l.lid)
      pstmt.setString(2, l.locationName)
      pstmt.setDouble(3, l.coordinates)
      pstmt.executeUpdate()
    }

    def fromDb(rs: ResultSet): List[Location] = {
      val lb: ListBuffer[Location] = new ListBuffer[Location]()
      while (rs.next()) lb.append(Location(rs.getInt("lid"), rs.getString("locationName"), rs.getDouble("coordinates")))
      lb.toList
    }

    def queryAll(con: Connection): ResultSet =
      query(con)("select * from Location")

  }

  case class Location(lid: Int, locationName: String, coordinates: Double) extends DbEntity[Location] {

    def reTable(stmt: Statement): Int = 0

    def toDb(c: Connection)(l: Location): Int = 0

    def fromDb(rs: ResultSet): List[Location] = List()

    def dropTableSql: String = "drop table if exists Location"

    def createTableSql: String = "create table Location (lid Integer, locationName String, coordinates Double)"

    def insertSql: String = "insert into Location (lid, locationName, coordinates) VALUES (?, ?, ?)"

  }

  //Table User

  object User extends DbEntity[User]{
    val dropTableSql = "drop table if exists User"
    val createTableSql = "create table User (uid Integer, firstname String, lastname String, telephone String)"
    val insertSql = "insert into User (uid, firstname, lastname, telephone) VALUES (?, ?, ?, ?)"


    def reTable(stmt: Statement): Int = {
      stmt.executeUpdate(User.dropTableSql)
      stmt.executeUpdate(User.createTableSql)
    }

    def toDb(c: Connection)(u: User): Int = {
      val pstmt = c.prepareStatement(insertSql)
      pstmt.setInt(1, u.uid)
      pstmt.setString(2, u.firstname)
      pstmt.setString(3, u.lastname)
      pstmt.setString(4, u.telephone)
      pstmt.executeUpdate()
    }

    def fromDb(rs: ResultSet): List[User] = {
      val lb: ListBuffer[User] = new ListBuffer[User]()
      while (rs.next()) lb.append(User(rs.getInt("uid"), rs.getString("firstname"), rs.getString("lastname"), rs.getString("telephone")))
      lb.toList
    }

    def queryAll(con: Connection): ResultSet =
      query(con)("select * from User")

  }

  case class User(uid: Int, firstname: String, lastname: String, telephone: String) extends DbEntity[User] {

    def reTable(stmt: Statement): Int = 0

    def toDb(c: Connection)(u: User): Int = 0

    def fromDb(rs: ResultSet): List[User] = List()

    def dropTableSql: String = "drop table if exists User"

    def createTableSql: String = "create table User (uid Integer, firstname String, lastname String, telephone String)"

    def insertSql: String = "insert into User (uid, firstname, lastname, telephone) VALUES (?, ?, ?, ?)"

  }

  //Table Employee

  object Employee extends DbEntity[Employee]{
    val dropTableSql = "drop table if exists Employee"
    val createTableSql = "create table Employee (eid Integer, firstname String, lastname String, roomNumber Integer, telephone String)"
    val insertSql = "insert into Employee (eid, firstname, lastname, roomNumber, telephone) VALUES (?, ?, ?, ?, ?)"


    def reTable(stmt: Statement): Int = {
      stmt.executeUpdate(Employee.dropTableSql)
      stmt.executeUpdate(Employee.createTableSql)
    }

    def toDb(c: Connection)(e: Employee): Int = {
      val pstmt = c.prepareStatement(insertSql)
      pstmt.setInt(1, e.eid)
      pstmt.setString(2, e.firstname)
      pstmt.setString(3, e.lastname)
      pstmt.setInt(4, e.roomNumber)
      pstmt.setString(5, e.telephone)
      pstmt.executeUpdate()
    }

    def fromDb(rs: ResultSet): List[Employee] = {
      val lb: ListBuffer[Employee] = new ListBuffer[Employee]()
      while (rs.next()) lb.append(Employee(rs.getInt("eid"), rs.getString("firstname"), rs.getString("lastname"), rs.getInt("roomNumber"), rs.getString("telephone")))
      lb.toList
    }

    def queryAll(con: Connection): ResultSet =
      query(con)("select * from employee")

  }

  case class Employee(eid: Int, firstname: String, lastname: String, roomNumber: Int, telephone: String) extends DbEntity[Employee] {

    def reTable(stmt: Statement): Int = 0

    def toDb(c: Connection)(e: Employee): Int = 0

    def fromDb(rs: ResultSet): List[Employee] = List()

    def dropTableSql: String = "drop table if exists Employee"

    def createTableSql: String = "create table Employee (eid Integer, firstname String, lastname String, roomNumber Integer, telephone String)"

    def insertSql: String = "insert into Employee (eid, firstname, lastname, roomNumber, telephone) VALUES (?, ?, ?, ?, ?)"

  }

  //Table Website

  object Website extends DbEntity[Website]{
    val dropTableSql = "drop table if exists Website"
    val createTableSql = "create table Website (wid Integer, name String,  costs Double, FQDN String)"
    val insertSql = "insert into Website (wid, name, costs, FQDN) VALUES (?, ?, ?, ?)"


    def reTable(stmt: Statement): Int = {
      stmt.executeUpdate(Website.dropTableSql)
      stmt.executeUpdate(Website.createTableSql)
    }

    def toDb(c: Connection)(w: Website): Int = {
      val pstmt = c.prepareStatement(insertSql)
      pstmt.setInt(1, w.wid)
      pstmt.setString(2, w.name)
      pstmt.setDouble(3, w.costs)
      pstmt.setString(4, w.FQDN)
      pstmt.executeUpdate()
    }

    def fromDb(rs: ResultSet): List[Website] = {
      val lb: ListBuffer[Website] = new ListBuffer[Website]()
      while (rs.next()) lb.append(Website(rs.getInt("wid"), rs.getString("name"), rs.getDouble("costs"), rs.getString("FQDN")))
      lb.toList
    }

    def queryAll(con: Connection): ResultSet =
      query(con)("select * from website")

  }

  case class Website(wid: Int, name: String, costs: Double, FQDN: String) extends DbEntity[Website] {

    def reTable(stmt: Statement): Int = 0

    def toDb(c: Connection)(w: Website): Int = 0

    def fromDb(rs: ResultSet): List[Website] = List()

    def dropTableSql: String = "drop table if exists Website"

    def createTableSql: String = "create table Website (wid Integer, name String,  costs Double, FQDN String)"

    def insertSql: String = "insert into Website (wid, name, costs, FQDN) VALUES (?, ?, ?, ?)"

  }
}




