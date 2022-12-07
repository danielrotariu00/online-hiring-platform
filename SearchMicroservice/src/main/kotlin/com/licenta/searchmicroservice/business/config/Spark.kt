package com.licenta.searchmicroservice.business.config

import org.apache.spark.SparkConf
import org.apache.spark.api.java.JavaSparkContext

object Spark {
    private val sparkConf: SparkConf = SparkConf()
        .setAppName("Search Microservice")
        .setMaster("spark://localhost:7077")
        .set("spark.io.encryption.enabled", "true")
        .set("spark.io.encryption.keySizeBits","256")
        .set("spark.executor.memory", "512m")
        .setJars(arrayOf(System.getProperty("user.dir") + "/out/artifacts/SearchMicroservice_jar/SearchMicroservice.jar"))

    val context = JavaSparkContext(sparkConf)
}