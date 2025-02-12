import com.github.music.of.the.ainur.almaren.builder.Core.Implicit 
import com.github.music.of.the.ainur.almaren.Almaren 
import org.apache.spark.sql.*
import java.io.*

val tempFile = File.createTempFile("gcs-key", ".json")
tempFile.deleteOnExit()
new PrintWriter(tempFile) {
  write(jsonContent)
  close()
}
spark.conf.set("spark.hadoop.google.cloud.auth.service.account.enable", "true")
spark.conf.set("spark.hadoop.google.cloud.auth.service.account.json.keyfile", tempFile.getAbsolutePath)
spark.conf.set("fs.gs.impl", "com.google.cloud.hadoop.fs.gcs.GoogleHadoopFileSystem")
spark.conf.set("fs.AbstractFileSystem.gs.impl", "com.google.cloud.hadoop.fs.gcs.GoogleHadoopFS")
spark.conf.set("fs.gs.project.id", "modak-nabu")

val almaren = Almaren("Demo1")
val spark = almaren.spark.getOrCreate()

val df_res = almaren.builder
.sourceFile("csv","gs://modak-training-bucket1/mt24048/nabu_custom_code/file2_updated.csv",Map("header"->"true","inferSchema"->"true"))
.alias("source")
.sql("select * from source")
.targetJdbc("jdbc:postgresql://w3.training5.modak.com:5432/postgres","org.postgresql.Driver","mt24048_nabu2",SaveMode.Overwrite,Some("mt24048"),Some("mt24048@m10y24"))
