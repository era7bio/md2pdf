package com.era7

import scala.sys.process._

/** The launched conscript entry point */
class md2pdf extends xsbti.AppMain {
  def run(config: xsbti.AppConfiguration) = {
    Exit(md2pdf.run(config.arguments))
  }
}

object md2pdf {
  /** Shared by the launched version and the runnable version,
   * returns the process status code */
  def run(args: Array[String]): Int = {

    val parser = new scopt.immutable.OptionParser[md2pdfConf] {

      def options = Seq(
        opt("o", "output", "output file") { 
          (v: String, c: md2pdfConf) => c.copy(output = v)
        },
        opt("t", "template", "template to use; defaults to template.tex" ) {
          (v: String, c: md2pdfConf) => c.copy(template = v)
        },
        intOpt("s", "tab-stop", "number of spaces considered a tab stop; defaults to 2") {
          (v: Int, c: md2pdfConf) => c.copy(tabStop = v)
        },
        booleanOpt("c", "toc", "you want a table of contents? defaults to true") {
          (v:Boolean, c: md2pdfConf) => c.copy(toc = v)
        },
        arg("<file>", "markdown input file") {
          (v: String, c: md2pdfConf) => c.copy(input = v)
        }
      )

    }

    parser.parse(args, md2pdfConf()) map { config =>

      CmdFromConf(config).!
    } getOrElse {

      1
    } 
  }
  /** Standard runnable class entrypoint */
  def main(args: Array[String]) {
    System.exit(run(args))
  }
}

case class Exit(val code: Int) extends xsbti.Exit

case class md2pdfConf(
                        input: String = "",
                        output: String =  "${HOME}/md2pdfOutput.pdf",
                        template: String = "template.tex",
                        tabStop: Int = 2,
                        toc: Boolean = true
                      )

object CmdFromConf {

  def apply(conf: md2pdfConf) =  Seq(
                                      "pandoc",
                                      "--from=markdown",
                                      "--latex-engine=xelatex",
                                      "--smart",
                                      "-V tables",
                                      if(conf.toc) "--toc" else "",
                                      "--tab-stop="+conf.tabStop.toString,
                                      "--template="+ conf.template,
                                      "--output="+ conf.output,
                                      conf.input
                                    )
}
