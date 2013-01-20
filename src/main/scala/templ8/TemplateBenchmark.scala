package templ8

import com.google.caliper.SimpleBenchmark
import org.apache.velocity.app.Velocity
import org.apache.velocity.VelocityContext
import model.Person
import java.io.StringWriter
import com.gilt.handlebars.Handlebars
import org.fusesource.scalate.TemplateEngine

class TemplateBenchmark extends SimpleBenchmark {
  val person = Person()

  println("Initializing Velocity")
  Velocity.init()
  var context = new VelocityContext
  context.put("person", person)
  var template = Velocity.getTemplate("src/main/resources/letter.vm")

  println("Initializing Scalate")
  val scalate = new TemplateEngine
  scalate.allowCaching = true
  scalate.allowReload = false
  val ssp = "src/main/resources/letter.ssp"

  println("Initializing Handlebars")
  val handlebarsTemplate = scala.io.Source.fromFile("src/main/resources/letter.handlebars").mkString
  val t = Handlebars(handlebarsTemplate)

  println("Initializing StringFormat template")
  val stringFormatTemplate = scala.io.Source.fromFile("src/main/resources/letter.sf").mkString


  def timeVelocityRendering(reps: Int): String = {
    var dummy: String = null

    for (i <- 1 to reps) {
      val sw = new StringWriter
      template.merge(context, sw)
      dummy = sw.toString
    }

    dummy
  }

  def timeHandlebarsRendering(reps: Int): String = {
    var dummy: String = null

    for (i <- 1 to reps) {
      dummy = t(person)
    }

    dummy
  }

  def timeScalateRendering(reps: Int): String = {
    var dummy: String = null
    for (i <- 1 to reps) {
      dummy = scalate.layout(ssp, Map("person" -> person))
    }

    dummy
  }

  def timeStringFormat(reps: Int): String = {
    var dummy: String = null
    for (i <- 1 to reps) {
      dummy = stringFormatTemplate.format(person.getFirstName, person.getLastName, person.getAge)
    }
    dummy
  }

  def timeStringBuilder(reps: Int): String = {
    var dummy: String = null
    for (i <- 1 to reps) {
      val sb = new StringBuilder
      sb.append("Dear ")
      sb.append(person.getFirstName)
      sb.append(",\n\nIt's good that your surname is ")
      sb.append(person.getLastName)
      sb.append(". We realise now that your age is ")
      sb.append(person.getAge)
      sb.append(".\n\nHappy Birthday.")
      dummy = sb.toString
    }
    dummy
  }

  def timeStringBuffer(reps: Int): String = {
    var dummy: String = null
    for (i <- 1 to reps) {
      val sb = new StringBuffer
      sb.append("Dear ")
      sb.append(person.getFirstName)
      sb.append(",\n\nIt's good that your surname is ")
      sb.append(person.getLastName)
      sb.append(". We realise now that your age is ")
      sb.append(person.getAge)
      sb.append(".\n\nHappy Birthday.")
      dummy = sb.toString
    }
    dummy
  }
}
