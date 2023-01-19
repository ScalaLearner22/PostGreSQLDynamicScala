package mainmethods

import model.Student
import database._

import java.sql.{Connection, ResultSet, Statement}
import scala.io.StdIn.{readInt, readLine}
import scala.util.control.Breaks.{break, breakable}

class StudentDAO extends PostgresConnector {
  classOf[org.postgresql.Driver]

  def createStatement(conn: Connection): Statement = {
    conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)
  }

  def createTable(stm: Statement): Int = {
    val sqlCreate = "CREATE TABLE Student(id INT PRIMARY KEY,name VARCHAR (4500) NOT NULL,subjects VARCHAR (3000) NOT NULL,marks INT)"
    stm.executeUpdate(sqlCreate)
  }

  def insert(stm: Statement, std: Student): Int = {
    val sqlInsert = s"INSERT INTO Student (id, name, subjects, marks) VALUES (${std.id}, '${std.name}', '${std.subjects}', ${std.marks})"

    stm.executeUpdate(sqlInsert)
  }

  def read(stm: Statement): List[Student] = {
    val sqlRead = "SELECT * from Student"
    val result = stm.executeQuery(sqlRead)

    def toIterator(resultSet: ResultSet): Iterator[ResultSet] = {
      new Iterator[ResultSet] { //way 1 also try by recursion
        def hasNext = resultSet.next()

        def next() = resultSet
      }
    }

    toIterator(result).map {
      o => Student(o.getInt("id"), o.getString("name"), o.getString("subjects"), o.getInt("marks"))

    }.toList
  }

  def updateValues(stm: Statement, set: Map[String, Any], condition: Map[String, Any]): Int = {
    val sqlUpdate = s"UPDATE Student SET ${set.map(p => s"${p._1}='${p._2}'").mkString(" , ")} WHERE ${condition.map(p => s"${p._1}='${p._2}'").mkString(" AND ")}"

    stm.executeUpdate(sqlUpdate)
  }

  def delete(stm: Statement, condition: Map[String, Any]): Int = {
    val sqlDelete = s"DELETE FROM Student WHERE ${condition.map(p => s"${p._1}='${p._2}'").mkString(" AND ")}"
    stm.executeUpdate(sqlDelete)
  }

  def connectionClose(conn: Connection): Unit = {
    conn.close()

  }
}

