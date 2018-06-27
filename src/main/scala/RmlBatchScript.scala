import sys.process._
import java.io.File
import scala.io.Source
import java.io.File
import java.io.FileWriter
import java.io.BufferedWriter

object RmlBatchScript {

  val jsonExtension = FileUtils.jsonExtension
  val datasetFolder = FileUtils.datasetFolder

  def writeRmlMappingFile(filePath: String) : String = {
    val file = Source.fromFile("edsajobs")
    val outputFile = new File(datasetFolder+filePath+".ttl")
    val writer = new BufferedWriter(new FileWriter(outputFile))
    // use curly brackets {} to tell Scala that it's now a multi-line statement!
    file.getLines().foreach{ line =>
      //println(line)
      writer.write(line.replace("file.json", s"${datasetFolder+filePath}.json"))
      writer.newLine()
    }
    writer.flush()
    writer.close()
    println(s"New file creates ${outputFile.getName}")
    outputFile.getName
  }

  def execRmlMapper(fileName: String, rmlFile: String) = {
    println(s"Executing RML Processor $fileName and $rmlFile")
    val result = s"cmd.exe /c java -jar RML-Mapper.jar -m ${datasetFolder+rmlFile} -o ${datasetFolder+fileName}.nt -f ntriples".!!
    println(result)
  }

  def main(args: Array[String]): Unit = {
    val files = FileUtils.getListOfFiles(datasetFolder)
    files.foreach { f =>
      val fileName = f.getName.replace(jsonExtension, "")
      println(s"Processing file $fileName")
      val rmlFile = writeRmlMappingFile(fileName)
      execRmlMapper(fileName, rmlFile)
    }
  }

}

//val result = "cmd.exe /c java -jar RML-Mapper.jar -m edsajobs.ttl -o edsajobs.rdf".!!
//val result = "cmd.exe /c echo hello".!
//val result = "cmd.exe /c dir".!
