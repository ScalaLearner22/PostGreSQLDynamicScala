package databasemethods

import database.PostgresConnector
import model.Student

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

object postGreSQLConnect {
  def main(args: Array[String]): Unit = {
    val a = new StudentDAO
    val b = a.getConnection()
    val c = a.createStatement(b)
    breakable {
      while (true) {
        println("\nWhat would you like to do: \n1. Create Table  \n2. Read Table \n3. Update Table \n4. Delete Table \n5. Exit")
        val ans = readInt()
        ans match {
          case 1 => a.createTable(c)
            val s1 = Student(1, "Aman", "History", 89)
            val s2 = Student(2, "Alex", "Maths", 70)
            val s3 = Student(3, "Alok", "English", 56)
            val s4 = Student(4, "Ankit", "Computers", 80)
            val s5 = Student(5, "Akshata", "Science", 85)
            val s6 = Student(8, "Daniel", "Marketing", 45)
            a.insert(c, s1)
            a.insert(c, s2)
            a.insert(c, s3)
            a.insert(c, s4)
            a.insert(c, s5)
            a.insert(c, s6)
          case 2 => a.read(c)
          case 3 =>
            println("Enter the name of columns to update: ")
            val u1: String = readLine()
            println("Enter the update Value")
            val u2: Any = readLine()
            println("Mention the condition to Update where")
            val i: String = readLine()
            println("The column value is: ")
            val k: Any = readLine()
            val t2 = Map(u1 -> u2)
            val t4 = Map(i -> k)
            a.updateValues(c, t2, t4)
          case 4 =>
            println("Enter the name of column you want to delete")
            val j1 = readLine()
            println("Mention the condition for")
            val j2: Any = readLine()
            val t3 = Map(j1 -> j2)

            a.delete(c, t3)

          case 5 =>
            a.connectionClose(b)
            break

        }

      }
    }
  }
}