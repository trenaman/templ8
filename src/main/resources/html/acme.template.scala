
package html

import play.templates._
import play.templates.TemplateMagic._


/**/
object acme extends BaseScalaTemplate[templ8.Html,Format[templ8.Html]](templ8.HtmlFormat) with play.api.templates.Template1[model.Employee,templ8.Html] {

    /**/
    def apply/*1.2*/(employee: model.Employee):templ8.Html = {
        _display_ {

Seq[Any](format.raw/*1.28*/("""

<html>
  <h2>Name: """),_display_(Seq[Any](/*4.14*/employee/*4.22*/.firstName)),format.raw/*4.32*/("""</h2>
  <h2>Surname: $"""),_display_(Seq[Any](/*5.18*/employee/*5.26*/.lastName)),format.raw/*5.35*/("""</h2>
  <h2>Age: """),_display_(Seq[Any](/*6.13*/employee/*6.21*/.age)),format.raw/*6.25*/("""</h2>
  <h2>Position: """),_display_(Seq[Any](/*7.18*/employee/*7.26*/.job.position)),format.raw/*7.39*/("""</h2>
  <h2>Department: """),_display_(Seq[Any](/*8.20*/employee/*8.28*/.job.department)),format.raw/*8.43*/("""</h2>
  <h2>Years In Position: """),_display_(Seq[Any](/*9.27*/employee/*9.35*/.job.yearsInPosition)),format.raw/*9.55*/("""</h2>
  <h2>Salary: """),_display_(Seq[Any](/*10.16*/employee/*10.24*/.job.salary)),format.raw/*10.35*/("""</h2>
  <h2>Projects:</h2>
  <ul>
    """),_display_(Seq[Any](/*13.6*/for(project <- employee.projects) yield /*13.39*/ {_display_(Seq[Any](format.raw/*13.41*/("""
    <li><h3>Code: """),_display_(Seq[Any](/*14.20*/project/*14.27*/.code)),format.raw/*14.32*/(""", Budget: """),_display_(Seq[Any](/*14.43*/project/*14.50*/.budget)),format.raw/*14.57*/(""", Keywords: """),_display_(Seq[Any](/*14.70*/project/*14.77*/.keywords)),format.raw/*14.86*/("""</h3></li>
    """)))})),format.raw/*15.6*/("""
  </ul>
</html>
"""))}
    }
    
    def render(employee:model.Employee) = apply(employee)
    
    def f:((model.Employee) => templ8.Html) = (employee) => apply(employee)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Sat Jan 26 23:15:05 GMT 2013
                    SOURCE: /Users/umatrangolo/Development/templ8/src/main/resources/acme.scala.html
                    HASH: 61270811469f4cc33a3d1615813391d50ebc7e4b
                    MATRIX: 268->1|359->27|416->49|432->57|463->67|521->90|537->98|567->107|620->125|636->133|661->137|719->160|735->168|769->181|829->206|845->214|881->229|948->261|964->269|1005->289|1062->310|1079->318|1112->329|1186->368|1235->401|1275->403|1331->423|1347->430|1374->435|1421->446|1437->453|1466->460|1515->473|1531->480|1562->489|1609->505
                    LINES: 12->1|15->1|18->4|18->4|18->4|19->5|19->5|19->5|20->6|20->6|20->6|21->7|21->7|21->7|22->8|22->8|22->8|23->9|23->9|23->9|24->10|24->10|24->10|27->13|27->13|27->13|28->14|28->14|28->14|28->14|28->14|28->14|28->14|28->14|28->14|29->15
                    -- GENERATED --
                */
            