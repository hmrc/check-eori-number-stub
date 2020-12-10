/*
 * Copyright 2020 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.checkeorinumberstub.services

import java.time.{ZoneId, ZonedDateTime}

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import uk.gov.hmrc.checkeorinumberstub.models.{CheckMultipleEoriNumbersRequest, CheckResponse, ProcessingDate}
import uk.gov.hmrc.smartstub._

class EisGeneratorSpec extends AnyWordSpec with Matchers with GuiceOneAppPerSuite{

  val checkData = CheckMultipleEoriNumbersRequest(List("GB123456789123","GB123456781123"))
  val processingDate: ProcessingDate = ZonedDateTime.now.withZoneSameInstant(ZoneId.of("Europe/London"))

  def generatedCheckResponse(seed: Long = 1L): Option[List[CheckResponse]] =
    EisGenerator.genEoriCheckResponse(checkData).seeded(seed)
      .map(x => x.map(_.copy(processingDate = processingDate)))

  "Generated data" should {

    "exist" in {
      generatedCheckResponse().nonEmpty shouldBe true
    }

    "be deterministic" in {
      generatedCheckResponse().get shouldBe generatedCheckResponse().get
    }
  }

}
