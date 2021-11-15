package app

import scala.collection.mutable.Stack

case class Menu(var items: Seq[MenuItem], var title: String = "", var prompt: String = "Option:")

abstract class MenuItem(val text: String) {
  def select(context: MenuSystem): Unit
}

case class Command(override val text: String, val command: (MenuSystem) => Unit) extends MenuItem(text) {
  override def select(context: MenuSystem) = {
    command(context)
  }
}

class Noop(override val text: String = "Do nothing") extends Command(text, (x) => {})
class Back(override val text: String = "Return to previous menu") extends Command(text, (x) => x.pop())

//Selecting a Submenu pushes a new Menu onto the stack
case class Submenu(override val text: String, val menu: Menu) extends MenuItem(text) {
  override def select(context: MenuSystem) {
    context.push(menu)
  }
}

class MenuSystem(menu: Menu) {
  var stack = new Stack[Menu]()
  push(menu)

  def push(menu: Menu) {
    stack.push(menu)
  }

  def pop() {
    if(stack.length > 0) {
      stack.pop()
    }
  }

  def render() {
    println(stack.top.title)
    if(stack.top.title.length > 0) {
      stack.top.title.foreach((x) => print('-'))
      println()
    }

    var i = 1
    stack.top.items.foreach((x) => {
      println(s" ${i}. ${x.text}")
      i += 1
    })

    print(s"\n${stack.top.prompt} ")
  }

  def select(itemNumber: Int) {
    stack.top.items(itemNumber - 1).select(this)
  }
}