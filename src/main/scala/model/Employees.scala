package model

import java.util.Date
import java.util.{ List => JList, ArrayList => JArrayList, Locale }

import org.thymeleaf.context.Context
import org.thymeleaf.context.IContext

import reflect.BeanProperty

import scala.collection.JavaConverters._

case class Employee(
  @BeanProperty val firstName: String,
  @BeanProperty val lastName: String,
  @BeanProperty val age: Int,
  @BeanProperty val job: Job,
  @BeanProperty val projects: JList[Project]
) {
  val asMap: Map[String, Any] =  Map( // needed for Scalate's Mustache
    "firstName" -> firstName,
    "lastName" -> lastName,
    "age" -> age,
    "job" -> job.asMap,
    "projects" -> (projects.asScala map { _.asMap }).asJava
  )

  val asIContext: IContext = new Context(
    Locale.US,
    Map("employee" -> this).asJava
  )
}

case class Job(
  @BeanProperty val position: String,
  @BeanProperty val department: String,
  @BeanProperty val yearsInPosition: Int,
  @BeanProperty val salary: Long
) {
  val asMap: Map[String, Any] = Map(
    "position" -> position,
    "department" -> department,
    "yearsInPosition" -> yearsInPosition,
    "salary" -> salary
  )
}

case class Project(
  @BeanProperty val code: String,
  @BeanProperty val budget: Long,
  @BeanProperty val keywords: JList[String]
) {
  val asMap: Map[String, Any] = Map(
    "code" -> code,
    "budget" -> budget,
    "keywords" -> keywords
  )
}

object Employee {
  import java.util.Random
  import java.math.BigInteger
  import scala.collection.JavaConverters._

  // use always the same seed to reproduce the test data
  val random = new Random(Option(System.getProperty("TEMPL8_SEED")).getOrElse("123").toLong)

  def randomString(rnd: Random, n: Int = 32): String = new BigInteger(130, rnd).toString(n)

  def randomProject(rnd: Random): Project = Project(
    code = randomString(rnd, 4),
    budget = rnd.nextLong(),
    keywords = (for { i <- 0 to rnd.nextInt(12) } yield { randomString(rnd, 16) }).toList.asJava
  )

  def randomJob(rnd: Random): Job = Job(
    position = randomString(rnd),
    department = randomString(rnd),
    yearsInPosition = rnd.nextInt(10),
    salary = Math.abs(rnd.nextLong())
  )

  def randomEmployee: Employee = Employee(
    firstName = randomString(random),
    lastName = randomString(random),
    age = random.nextInt(100),
    job = randomJob(random),
    projects = (for { i <- 0 to random.nextInt(32) } yield { randomProject(random) }).toList.asJava
  )
}
