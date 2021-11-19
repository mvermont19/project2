package app.cli.menu

import app._
import app.cli._
import data.analysis._
import data.analysis.Analysis._

object DateChoice extends Menu(
                                    Seq[MenuItem](),
                                    "Which cryptocurrency?"
                                    ) {
                                        items = Seq()

                                        CRYPTOCURRENCIES.foreach(x => {
                                        items = items :+ Submenu(
                                            s"${x._1} (${x._2})",
                                            Menu(
                                                Seq(
                                                    Command("Specific Day", _ => specificDate(1, x._1)),
                                                    Command("Specifc Week", _ => specificDate(2, x._1)),
                                                    Command("Specific Month", _ => specificDate(3, x._1)),
                                                    Command("Specifc Year", _ => specificDate(4, x._1)),
                                                    new Back()
                                                ),
                                                "Please Choose a Time Frame"
                                            ))
                                        })

                                    items = items :+ new Back()
                                    }
