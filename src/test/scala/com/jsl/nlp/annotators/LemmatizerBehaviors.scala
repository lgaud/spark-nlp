package com.jsl.nlp.annotators

import com.jsl.nlp.{Annotation, AnnotatorBuilder, Document}
import org.apache.spark.sql.{Dataset, Row}
import org.scalatest._

/**
  * Created by saif on 01/05/17.
  */
trait LemmatizerBehaviors { this: FlatSpec =>

  def fullLemmatizerPipeline(dataset: => Dataset[Row]) {
    "a Lemmatizer Annotator" should "succesfully transform data" in {
      println(dataset.schema)
      AnnotatorBuilder.withFullLemmatizer(dataset)
        .collect().foreach {
        row =>
          val document = Document(row.getAs[Row](0))
          println(document)
          row.getSeq[Row](2)
            .map(Annotation(_))
            .foreach {
              case lemma: Annotation if lemma.annotatorType == Lemmatizer.annotatorType =>
                println(lemma, document.text.substring(lemma.begin, lemma.end), lemma.metadata.mkString(", "))
              case _ => ()
            }
      }
    }
  }
}