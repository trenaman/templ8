package templ8
import com.google.caliper.{Runner => CaliperRunner}

object Runner {
  def main(args: Array[String]) {
    val caliperArgs = ("-Jmemory=-Xmx1G" :: Nil).toArray
    CaliperRunner.main(classOf[TemplateBenchmark], caliperArgs)
  }
}
