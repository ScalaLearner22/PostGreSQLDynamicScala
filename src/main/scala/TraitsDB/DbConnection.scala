package TraitsDB
import java.sql.Connection
import java.util.Properties

trait DbConnection {
  def getConnection():Connection
//  def createStatement(conn: Connection): Statement
//  def createTable(stm: Statement, tableName: String): ResultSet
//  def insert(stm: Statement, tableName: String): ResultSet
//  def read(stm: Statement, database: String): ResultSet
//  def getValues(rs: ResultSet, fieldName: String): String
//  def updateValues(stm: Statement, database: String): ResultSet
//  def delete(stm: Statement, database: String, colName: Any): ResultSet
//  def connectionClose(conn: Connection): Option[Unit]
}

trait PostgresConnector extends DbConnection {

  def getConnection(): Connection = {
    println("Postgres connector")
    import java.sql.{Connection, DriverManager}
    val url = "jdbc:postgresql://localhost:5432/Project1_DB"
    val props = new Properties()
    props.setProperty("user", "postgres")
    props.setProperty("password", "postgres")
    //props.setProperty("ssl", "true")
    DriverManager.getConnection(url, props)
  }

}

