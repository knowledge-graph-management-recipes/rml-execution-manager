import java.io.{BufferedWriter, File, FileWriter}

import RmlBatchScript.datasetFolder
import play.api.libs.json._

import scala.io.Source

object SkillsScript {

  def main(args: Array[String]): Unit = {
    println("Starting to annotate the skills")
    val files = FileUtils.getListOfFiles(datasetFolder)
    files.foreach { f =>
      val fileName = datasetFolder+f.getName
      println(s"Extracting skills from file $fileName")

      val outputFile = new File(datasetFolder+f.getName.replace(".json",".nt"))
      val writer = new BufferedWriter(new FileWriter(outputFile))

      val json = Json.parse(Source.fromFile(fileName).getLines().mkString)
      (json \ "results").as[List[JsObject]].map{ r =>
        val uri = (r \ "uri").as[String]
        (r \ "requiredSkills").as[List[JsObject]].map { skill =>
          val skillUri = (skill \ "uri").as[String]
          writer.write(addSkillTriple(uri, skillUri))
          writer.newLine()
        }
      }

      writer.flush()
      writer.close()
      println(s"New file created ${outputFile.getName}")

    }
  }

  def addSkillTriple(uri: String, skillUri: String) : String = {
    val skillUriEncoded = skillUri.replace(" ","%20")//java.net.URLEncoder.encode(skillUri, "utf-8")
    s"<http://www.edsa-project.eu/jobpost/$uri> <http://www.edsa-project.eu/edsa#requiredSkill> <$skillUriEncoded> ."
  }

}
