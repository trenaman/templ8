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

  Velocity.init
  var context = new VelocityContext
  context.put("person", person)
  var template = Velocity.getTemplate("src/main/resources/letter.vm");

  def timeVelocityRendering(reps: Int): String = {

    for (i <- 0 to reps) {
      val sw = new StringWriter
      template.merge(context, sw)
      val s = sw.toString
    }

    return ""
  }

  val handlebarsTemplate = scala.io.Source.fromFile("src/main/resources/letter.handlebars").mkString
  val t = Handlebars(handlebarsTemplate)

  def timeHandlebarsRendering(reps: Int): String = {
    for (i <- 0 to reps) {
      val s = t(person)
    }

    ""
  }

  val scalate = new TemplateEngine
  scalate.allowCaching = true
  scalate.allowReload = false
  val ssp = "src/main/resources/letter.ssp"

  def timeScalateRendering(reps: Int): String = {
    for (i <- 0 to reps) {
      val s = scalate.layout(ssp, Map("person" -> person))
    }

    ""
  }

}
