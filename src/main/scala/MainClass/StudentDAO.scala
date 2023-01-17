package MainClass

import CaseClassDB.Student
import TraitsDB._

import java.sql.{Connection, ResultSet, Statement}
import scala.io.StdIn.readLine

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
    //val sqlInsert = s"INSERT INTO Student (id, name, subjects, marks) VALUES (_, _, _, _)"

    stm.executeUpdate(sqlInsert)
  }

  def read(stm: Statement): List[Student] = {
    val sqlRead = "SELECT * from Student"
    val result = stm.executeQuery(sqlRead) //read //executequery

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
  //    while (result.next) {
  //      //val v = result.getString(s"$fieldName")
  //      val s=Student(result.getInt("id"),result.getString("name"),result.getString("subject"),56)
  //
  //    }
  //}

  def updateValues(stm: Statement, set: Map[String, Any], condition: Map[String, Any]): Int = {
    //val k=value.map(l=>l._1)
//    val a=12
//    println(a.toString)
    //val k="12"
    //val sqlUpdate = s"UPDATE Student SET $col2.=?'$col2Update'=?,$col3=?'$col3Update=?' WHERE $col1=?;"
    val sqlUpdate = s"UPDATE Student SET ${set.map(p=>s"${p._1}='${p._2}'").mkString(" , ") } WHERE ${condition.map(p=>s"${p._1}='${p._2}'").mkString(" AND ")}"

 // name='ok'
 // val g=set.map(p=>s"${p._1}='${p._2}'")
  stm.executeUpdate(sqlUpdate)
  }

  def delete(stm: Statement, condition: Map[String,Any]): Int = {
    val sqlDelete = s"DELETE FROM Student WHERE ${condition.map(p=>s"${p._1}='${p._2}'").mkString(" AND ")}"
    stm.executeUpdate(sqlDelete)
  }

  def connectionClose(conn: Connection): Unit = {
    conn.close()

  }

}
object postGreSQLConnect {
  def main(args: Array[String]): Unit = {
    val a=new StudentDAO
    val b=a.getConnection()
    val c=a.createStatement(b)
    a.createTable(c)
    val s1= Student(1,"Aman","History",89)
    val s2= Student(2,"Alex","Maths",70)
    val s3= Student(3,"Alok","English",56)
    val s4= Student(4,"Ankit","Computers",80)
    val s5=Student(5,"Akshata","Science",85)
    val s6=Student(8,"Daniel","Marketing",45)
    a.insert(c,s1)
    a.insert(c,s2)
    a.insert(c,s3)
    a.insert(c,s4)
    a.insert(c,s5)
    a.insert(c,s6)

    a.read(c)
    println("Enter the name of columns to update: ")
    val u1: String = readLine()
    println("Enter the update Value")
    val u2: Any=readLine()
    println("Mention the condition to Update where")
    val i: String= readLine()
    println("The column value is: ")
    val k:Any=readLine()
    val t2=Map(u1-> u2)
    val t4=Map(i -> k)
    a.updateValues(c,t2,t4)
    println("Do you want to delete any columns?")
    val p: String=readLine()
    if(p=="Yes"|| p=="YES")
    {println("Enter the name of column you want to delete")
    val j1=readLine()
    println("Mention the condition for")
    val j2: Any= readLine()
    val t3= Map(j1 -> j2)
//    val m3=t3.productIterator.map{
//      case(j1,j2) => (j1.toString -> j2)
//    }.toMap

    a.delete(c,t3)}
    else{
    //statement.executeUpdate(sqlInsert)
    //val k=c.setString()//input
  a.connectionClose(b)}




  }}