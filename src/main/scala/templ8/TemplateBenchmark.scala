package templ8

import com.gilt.handlebars.Handlebars

import com.google.caliper.SimpleBenchmark

import freemarker.ext.beans.BeansWrapper
import freemarker.template.{DefaultObjectWrapper, Configuration}

import java.io.{ File, StringWriter }
import java.util

import model.Employee._

import model._

import org.apache.velocity.VelocityContext
import org.apache.velocity.app.Velocity

import org.fusesource.scalate.{ TemplateEngine => STemplateEngine }

import play.templates.ScalaTemplateCompiler

import scala.collection.JavaConverters._

object AllRandomEmployees {
  private val rndEmployees: Array[Employee] = (for { i <- 0 to 10000 } yield { Employee.randomEmployee }).toArray
  def rndEmployee(i: Int): Employee = rndEmployees(i % rndEmployees.size)
}

object Engines {
  Velocity.init()
  var velocityTemplate = Velocity.getTemplate("src/main/resources/acme.vm")

  val scalate = new STemplateEngine
  scalate.workingDirectory = new File("data/scalate")
  scalate.allowCaching = true // false will sky rocket runtime
  scalate.allowReload = false
  val ssp = "src/main/resources/acme.ssp"
  val warmUpSsp = scalate.layout(ssp, Map("employee" -> AllRandomEmployees.rndEmployee(0)))
  val mustache = "src/main/resources/acme.mustache"
  val warmUpMustache = scalate.layout(mustache, AllRandomEmployees.rndEmployee(0).asMap)

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
}

class TemplateBenchmark extends SimpleBenchmark {
  import Engines._

  def timeScalateSSP(reps: Int): String = {
    var dummy: String = null
    var i = 0
    while (i < reps) {
      dummy = scalate.layout(ssp, Map("employee" -> AllRandomEmployees.rndEmployee(i)))
      i += 1
    }

    dummy
  }

  def timeScalateMustache(reps: Int): String = {
    var dummy: String = null

    var i = 0
    while (i < reps) {
      dummy = scalate.layout(mustache, AllRandomEmployees.rndEmployee(i).asMap)
      i += 1
    }

    dummy
  }

  def timeHandlebarsScala(reps: Int): String = {
    var dummy: String = null

    var i = 0
    while (i < reps) {
      val employee = AllRandomEmployees.rndEmployee(i)
      dummy = handlebars(employee)
      i += 1
    }
    dummy
  }

  def timePlay20(reps: Int): String = {
    var dummy: String = null
    var i = 0
    while (i < reps) {
      val employee = AllRandomEmployees.rndEmployee(i)
      dummy = acme.render(employee).toString
      i += 1
    }

    dummy
  }

  def timeFreemarker(reps: Int): String = {
    var dummy: String = null

    var i = 0
    while (i < reps) {
      val sw = new StringWriter
      val employee = AllRandomEmployees.rndEmployee(i)
      freemarkerTemplate.process(employee, sw)
      dummy = sw.toString
      i += 1
    }

    dummy
  }

  def timeVelocity(reps: Int): String = {
    var dummy: String = ""

    var i = 0
    while (i < reps) {
      val sw = new StringWriter
      val employee = AllRandomEmployees.rndEmployee(i)
      var context = new VelocityContext
      context.put("employee", employee)
      velocityTemplate.merge(context, sw)
      dummy = sw.toString

      i += 1
    }

    dummy
  }

  def timeStringFormat(reps: Int): String = {
    var dummy: String = null
    var i = 0
    while (i < reps) {
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
      i += 1
    }

    dummy
  }

  def timeStringBuffer(reps: Int): String = {
    var dummy: String = null

    var i = 0
    while (i < reps) {
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

      i += 1
    }

    dummy
  }

  def timeStringBuilder(reps: Int): String = {
    var dummy: String = null

    var i = 0
    while (i < reps) {
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

      i += 1
    }

    dummy
  }

  def timeDumbStringConcatenation(reps: Int): String = {
    var dummy: String = null

    var i = 0
    while (i < reps) {
      var s = ""
      val employee = AllRandomEmployees.rndEmployee(i)

      s = s + "<html>\n" + "<h1>All employees:</h1>\n" + "<h2>Name:" + employee.firstName + "</h2>\n" +
        "  <h2>Surname:" + employee.lastName + "</h2>\n" +
        "  <h2>Age:" +employee.age + "</h2>\n" +
        "  <h2>Position:" + employee.job.position + "</h2>\n" +
        "  <h2>Department:" + employee.job.department + "</h2>\n" +
        "  <h2>Years In Position:" + employee.job.yearsInPosition + "</h2>\n" +
        "  <h2>Salary:" + employee.job.salary + "</h2>\n" +
        "  <h2>Projects:</h2>\n" +
        "  <ul>\n"

        val numProjects = employee.projects.size
        var project = 0
        while (project < numProjects) {
          val p = employee.projects.get(project)

          s += "    <li><h3>Code:" + p.code + ", Budget:" + p.budget + ", Keywords:\n"

          val numKeywords = p.keywords.size
          var keyword = 0
          while (keyword < numKeywords) {
            val k = p.keywords.get(keyword)
            s +=  k + ", \n"
            keyword += 1
          }

          s += "    </h3></li>\n"
          project += 1
        }

        s +=  "  </ul>\n" + "</html>\n"

      dummy = s
      i += 1
    }

    dummy
  }
}

// Play 2.0 template utils
// Utils needed to compile a Play template into Scala code. To update after a new version
// of Play template:
//
// 1. Update the Play template prj version in project/Build.scala
// 2. sbt console
// 3. templ8.PlayUtils.recompile()
// 4. mv src/main/resources/html/acme.template.scala src/main/scala/templ8
// 5. edit acme.template.scala and change its classpath from `html` to `templ8`
// 6. sbt compile to make sure it works
// TODO this could be auto but is not worth the 5 mins of work
import play.templates._

case class Html(text: String) extends Appendable[Html] {
  val buffer = new StringBuilder(text)
  def +(other: Html) = {
    buffer.append(other.buffer)
    this
  }
  override def toString = buffer.toString
}

object HtmlFormat extends Format[Html] {
  def raw(text: String) = Html(text)
  def escape(text: String) = Html(text.replace("<", "&lt;"))
}

object PlayUtils {
  val playTemplateCompiler = ScalaTemplateCompiler
  val playTemplate = new File("src/main/resources/acme.scala.html")
  val playTemplateFolder = new File("src/main/resources")
  val playTemplateGeneratedFolder = new File("src/main/resources")

  def recompile(): Option[File] = {
    playTemplateCompiler.compile(
      source = playTemplate,
      sourceDirectory = playTemplateGeneratedFolder,
      generatedDirectory = playTemplateGeneratedFolder,
      resultType = "templ8.Html",
      formatterType = "templ8.HtmlFormat"
    )
  }
}
