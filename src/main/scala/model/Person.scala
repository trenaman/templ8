package model

import reflect.BeanProperty

case class Person (
  @BeanProperty val firstName: String,
  @BeanProperty val lastName: String,
  @BeanProperty val age: Int
)

object Person {
  def apply(): Person = Person("Ade", "Trenaman", 39)
}