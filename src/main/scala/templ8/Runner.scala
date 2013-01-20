package templ8
import com.google.caliper.{Runner => CaliperRunner}

object Runner {
  def main(args: Array[String]) {
    CaliperRunner.main(classOf[TemplateBenchmark], args)
  }
}