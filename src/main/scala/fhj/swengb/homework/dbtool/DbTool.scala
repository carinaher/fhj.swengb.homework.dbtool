package fhj.swengb.homework.dbtool

import java.awt.Button
import java.net.URL
import java.sql.{Connection, DriverManager, ResultSet, Statement}
import java.util.ResourceBundle
import javafx.application.Application
import javafx.collections.{FXCollections, ObservableList}
import javafx.scene.control.{TextArea, Label, ListView}
import javafx.scene.image.Image
import javafx.scene.layout.BorderPane

import fhj.swengb.Person._
import fhj.swengb.homework.dbtool.db_table.{Location, User}
import fhj.swengb.{Db, Speakers, Person, Students}

import javafx.fxml._
import javafx.scene._
import javafx.stage._

import scala.util.Try
import scala.util.control.NonFatal


// javafx - Application
class DbTool extends javafx.application.Application {

  val Fxml = "/fhj/swengb/homework/dbtool/dbtool.fxml"
  val Css = "fhj/swengb/homework/dbtool/dbtool.css"

  val loader = new FXMLLoader(getClass.getResource(Fxml))

  override def start(stage: Stage): Unit =
    try {
      stage.setTitle("dbTool")
      loader.load[Parent]()
      val scene = new Scene(loader.getRoot[Parent]) //loads the default scene
      stage.setScene(scene)
      stage.setResizable(false)
      stage.getScene.getStylesheets.add(Css)
      stage.show()

    } catch {
      case NonFatal(e) => e.printStackTrace()
    }

}

class DbToolController extends Initializable {
  @FXML var input: Label = _
  @FXML var output: Label = _


  override def initialize(location: URL, resources: ResourceBundle): Unit = {
    input.setText("Input")
    output.setText("Output")

  }
}

object DbTool {

  val userOne: User = User(1, "Angelo", "Merte", "0800666666")
  val userTwo: User = User(2, "Angela", "Mertina", "0800777777")
  val userThree: User = User(3, "Angel", "Devil", "0800888888")
  val userFour: User = User(4, "Devil", "Angola", "0815")

  val users: Set[User] = Set(userOne, userTwo, userThree, userFour)

  val locationOne: Location = Location(1, "Scheißegalstraße", 66.66)
  val locationTwo: Location = Location(2, "Schleichdi", 77.77)
  val locationThree: Location = Location(3, "Ostblock", 88.88)
  val locationFour: Location = Location(4, "Dreckigweg", 99.99)

  val locations: Set[Location] = Set(locationOne, locationTwo, locationThree, locationFour)


  def main(args: Array[String]) {
    Application.launch(classOf[DbTool], args: _*)
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