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
import uk.gov.hmrc.checkeorinumberstub.models.{CheckMultipleEoriNumbersRequest, ProcessingDate}
import uk.gov.hmrc.smartstub._

class EisGeneratorSpec extends AnyWordSpec with Matchers with GuiceOneAppPerSuite{

  val checkData = CheckMultipleEoriNumbersRequest(List("GB123456789123","GB123456781123"))

  "Generated data" should {

    "exist" in {
      EisGenerator.genEoriCheckResponse(checkData).seeded(1L).nonEmpty shouldBe true
    }

    "be deterministic" in {
      val processingDate: ProcessingDate = ZonedDateTime.now.withZoneSameInstant(ZoneId.of("Europe/London"))
      val gen1 = EisGenerator.genEoriCheckResponse(checkData).seeded(1L).get
        .map(x => x.copy(processingDate = processingDate))
      val gen2 = EisGenerator.genEoriCheckResponse(checkData).seeded(1L).get
        .map(x => x.copy(processingDate = processingDate))
      gen1 shouldBe gen2
    }
  }

}
