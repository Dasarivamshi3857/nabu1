import com.github.music.of.the.ainur.almaren.builder.Core.Implicit 
import com.github.music.of.the.ainur.almaren.Almaren 
import org.apache.spark.sql.*

val almaren = Almaren("Demo1")
val spark = almaren.spark.getOrCreate()

val df_res = almaren.builder
.sourceFile("csv","gs://modak-training-bucket1/mt24048/nabu_custom_code/file2_updated.csv",Map("header"->"true","inferSchema"->"true"))
.alias("source")
.sql("select * from source")
.targetJdbc("jdbc:postgresql://w3.training5.modak.com:5432/postgres","org.postgresql.Driver","mt24048_nabu2",SaveMode.Overwrite,Some("mt24048"),Some("mt24048@m10y24"))
