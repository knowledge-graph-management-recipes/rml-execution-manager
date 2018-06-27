import java.io.File

object FileUtils {

  val jsonExtension = ".json"
  val datasetFolder = "./dataset/"

  def getListOfFiles(dir: String):List[File] = {
    val d = new File(dir)
    if (d.exists && d.isDirectory)
      d.listFiles.filter{ f => f.isFile && f.getName.endsWith(jsonExtension)}.toList
    else
      List[File]()
  }

}
