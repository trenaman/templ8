package templ8

import com.gilt.handlebars.Handlebars

import com.google.caliper.SimpleBenchmark

import freemarker.ext.beans.BeansWrapper
import freemarker.template.{DefaultObjectWrapper, Configuration}

import java.io.{ File, StringWriter }
import java.util

import model._
import model.Employee._

import org.apache.velocity.VelocityContext
import org.apache.velocity.app.Velocity

import org.fusesource.scalate.TemplateEngine

import scala.collection.JavaConverters._

object AllRandomEmployees {
  private val rndEmployees: List[Employee] = (for { i <- 0 to 10000 } yield { Employee.randomEmployee }).toList
  def rndEmployee(i: Int): Employee = rndEmployees(i % rndEmployees.size)
}

class TemplateBenchmark extends SimpleBenchmark {

  Velocity.init()
  var velocityTemplate = Velocity.getTemplate("src/main/resources/acme.vm")

  val scalate = new TemplateEngine
  scalate.allowCaching = true
  scalate.allowReload = false
  val ssp = "src/main/resources/acme.ssp"

  val handlebarsTemplate = scala.io.Source.fromFile("src/main/resources/acme.handlebars").mkString
  val handlebars = Handlebars(handlebarsTemplate)

  val MainStringFormatTemplate =
    """
     | <html>
     |   <h2>Name: %s</h2>
     |   <h2>Surname: %s</h2>
     |   <h2>Age: %s</h2>
     |   <h2>Position: %s</h2>
     |   <h2>Department: %s</h2>
     |   <h2>Years In Position: %s</h2>
     |   <h2>Salary: %s</h2>
     |   <h2>Projects:</h2>
     |   <ul>
     |     %s
     |   </ul>
     | </html>
    """.stripMargin

  val ProjectsStringFormatTemplate = "<li><h3>Code: %s, Budget: %s, Keywords: %s</h3></li></ul></html>"

  val freemarkerConfiguration = new Configuration()
  freemarkerConfiguration.setDirectoryForTemplateLoading(new File("src/main/resources/"))
  freemarkerConfiguration.setObjectWrapper(new DefaultObjectWrapper)
  val freemarkerTemplate = freemarkerConfiguration.getTemplate("acme.ftl")

  def timeVelocityRendering(reps: Int): String = {
    var dummy: String = ""

    for (i <- 1 to reps) {
      val sw = new StringWriter
      val employee = AllRandomEmployees.rndEmployee(i)
      var context = new VelocityContext
      context.put("employee", employee)
      velocityTemplate.merge(context, sw)
      dummy = sw.toString
    }

    dummy
  }

  def timeHandlebarsRendering(reps: Int): String = {
    var dummy: String = null

    for (i <- 1 to reps) {
      val employee = AllRandomEmployees.rndEmployee(i)
      dummy = handlebars(employee)
    }
    dummy
  }


  // commenting out given that is way too slow and is hiding others
  // def timeScalateSSPRendering(reps: Int): String = {
  //   var dummy: String = null

  //   for (i <- 1 to reps) {
  //     dummy = scalate.layout(ssp, Map("employee" -> AllRandomEmployees.rndEmployee(i)))
  //   }
  //   dummy
  // }

  // TODO Scalate Mustache
  // TODO Scalate SCAML
  // TODO Play templates

  def timeFreemarker(reps: Int): String = {
    var dummy: String = null

    for (i <- 1 to reps) {
      val sw = new StringWriter
      val employee = AllRandomEmployees.rndEmployee(i)
      freemarkerTemplate.process(employee, sw)
      dummy = sw.toString
    }

    dummy
  }

  def timeStringFormat(reps: Int): String = {
    var dummy: String = null
    for (i <- 1 to reps) {
      val employee = AllRandomEmployees.rndEmployee(i)
      dummy = MainStringFormatTemplate.format(
        employee.firstName,
        employee.lastName,
        employee.age,
        employee.job.position,
        employee.job.department,
        employee.job.yearsInPosition,
        employee.job.salary,
        {
           (employee.projects.asScala map { p => ProjectsStringFormatTemplate.format(p.code, p.budget, p.keywords) }).mkString("\n")
        }
      )
    }

    dummy
  }

  def timeStringBuffer(reps: Int): String = {
    var dummy: String = null

    for (i <- 1 to reps) {
      val sb = new StringBuffer()
      val employee = AllRandomEmployees.rndEmployee(i)
      sb.append("<html>\n")
      sb.append("  <h2>Name:").append(employee.firstName).append("</h2>\n")
      sb.append("  <h2>Surname:").append(employee.lastName).append("</h2>\n")
      sb.append("  <h2>Age:").append(employee.age).append("</h2>\n")
      sb.append("  <h2>Position:").append(employee.job.position).append("</h2>\n")
      sb.append("  <h2>Department:").append(employee.job.department).append("</h2>\n")
      sb.append("  <h2>Years In Position:").append(employee.job.yearsInPosition).append("</h2>\n")
      sb.append("  <h2>Salary:").append(employee.job.salary).append("</h2>\n")
      sb.append("  <h2>Projects:").append("</h2>\n")
      sb.append("  <ul>\n")
      employee.projects.asScala foreach { p =>
        sb.append("    <li><h3>Code:").append(p.code).append(", Budget:").append(p.budget).append(", Keywords:")
        p.keywords.asScala foreach { k => sb.append(k).append(", ") }
        sb.append("</h3></li>\n")
      }
      sb.append("  </ul>\n")
      sb.append("</html>\n")
      dummy = sb.toString
    }

    dummy
  }

  def timeStringBuilder(reps: Int): String = {
    var dummy: String = null

    for (i <- 1 to reps) {
      val sb = new StringBuilder()
      val employee = AllRandomEmployees.rndEmployee(i)
      sb.append("<html>\n")
      sb.append("  <h2>Name:").append(employee.firstName).append("</h2>\n")
      sb.append("  <h2>Surname:").append(employee.lastName).append("</h2>\n")
      sb.append("  <h2>Age:").append(employee.age).append("</h2>\n")
      sb.append("  <h2>Position:").append(employee.job.position).append("</h2>\n")
      sb.append("  <h2>Department:").append(employee.job.department).append("</h2>\n")
      sb.append("  <h2>Years In Position:").append(employee.job.yearsInPosition).append("</h2>\n")
      sb.append("  <h2>Salary:").append(employee.job.salary).append("</h2>\n")
      sb.append("  <h2>Projects:").append("</h2>\n")
      sb.append("  <ul>\n")
      employee.projects.asScala foreach { p =>
        sb.append("    <li><h3>Code:").append(p.code).append(", Budget:").append(p.budget).append(", Keywords:")
        p.keywords.asScala foreach { k => sb.append(k).append(", ") }
        sb.append("</h3></li>\n")
      }
      sb.append("  </ul>\n")
      sb.append("</html>\n")
      dummy = sb.toString
    }

    dummy
  }

  def timeDumbStringConcatenation(reps: Int): String = {
    var dummy: String = null

    for (i <- 1 to reps) {
      var s = ""
      val employee = AllRandomEmployees.rndEmployee(i)
      s = s + "<html>\n" + "<h1>All employees:</h1>\n" + "<h2>Name:" + employee.firstName + "</h2>\n"
      s = s + "  <h2>Surname:" + employee.lastName + "</h2>\n"
      s = s + "  <h2>Age:" +employee.age + "</h2>\n"
      s = s + "  <h2>Position:" + employee.job.position + "</h2>\n"
      s = s + "  <h2>Department:" + employee.job.department + "</h2>\n"
      s = s + "  <h2>Years In Position:" + employee.job.yearsInPosition + "</h2>\n"
      s = s + "  <h2>Salary:" + employee.job.salary + "</h2>\n"
      s = s + "  <h2>Projects:</h2>\n"
      s = s + "  <ul>\n"
      employee.projects.asScala foreach { p =>
        s = s + "    <li><h3>Code:" + p.code + ", Budget:" + p.budget + ", Keywords:\n"
        p.keywords.asScala foreach { k => s = s + k + ", \n" }
        s = s + "    </h3></li>\n"
      }
      s = s + "  </ul>\n"
      s = s + "</html>\n"

      dummy = s
    }

    dummy
  }
}
