package database

import java.sql.Connection
import java.util.Properties

trait DbConnection {
  def getConnection():Connection
}

trait PostgresConnector extends DbConnection {

  def getConnection(): Connection = {
    println("Postgres connector")
    import java.sql.DriverManager
    val url = "jdbc:postgresql://localhost:5432/Project1_DB"
    val props = new Properties()
    props.setProperty("user", "postgres")
    props.setProperty("password", "postgres")
    //props.setProperty("ssl", "true")
    DriverManager.getConnection(url, props)
  }

}

