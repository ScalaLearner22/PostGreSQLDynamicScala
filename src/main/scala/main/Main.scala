package main

import mainmethods.StudentDAO
import model.Student

import scala.io.StdIn.{readInt, readLine}
import scala.util.control.Breaks.{break, breakable}

object Main {
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
