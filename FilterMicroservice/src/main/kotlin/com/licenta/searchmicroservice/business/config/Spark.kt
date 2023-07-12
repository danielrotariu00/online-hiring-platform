package com.licenta.searchmicroservice.business.config

import org.apache.spark.SparkConf
import org.apache.spark.api.java.JavaSparkContext

object Spark {
    private val sparkConf: SparkConf = SparkConf()
        .setAppName("Filter Microservice")
        .setMaster("spark://${System.getenv("SPARK_MASTER_HOST")}:${System.getenv("SPARK_MASTER_PORT")}")
        .set("spark.io.encryption.enabled", "true")
        .set("spark.io.encryption.keySizeBits","256")
        .set("spark.executor.memory", "512m")
        .setJars(arrayOf("FilterMicroservice.jar"))

    val context = JavaSparkContext(sparkConf)
}
