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

import org.scalacheck.Gen
import cats.implicits._
import org.scalacheck.support.cats._
import uk.gov.hmrc.smartstub._
import uk.gov.hmrc.checkeorinumberstub.models.{Address, CheckRequest, CheckResponse, EoriNumber}


object EisGenerator {

  //TODO Check with Ian the format of TraderName

  private lazy val genTraderName: Gen[String] = {
    Gen.company.retryUntil(a => a.length < 35 && a.length > 1)
  }

   lazy val genFirstAndLastName: Gen[String] = {
    for {
      fname <- Gen.forename
      sname <- Gen.surname
    } yield s"$fname $sname"
  }

  private lazy val genEoriNumber: Gen[String] = {
    for {
      a <- Gen.const("GB")
      b <- Gen.numStr.retryUntil(a => a.length < 15 && a.length > 1)
    } yield s"$a$b"
  }

  private lazy val genAddress: Gen[Address] = {
    for {
      a <- Gen.oneOf("50 George Street", "10 Park Avenue", "12 Baker Street")
      b <- Gen.oneOf("Brighton", "Liverpool", "London")
      c  <- Gen.oneOf("BN11 5HA", "L1 5QT", "SS13 4FT")
    } yield Address(a, b, c)
  }

//  private lazy val addressGen: Gen[Address] = {
//    Gen.ukAddress.map { lines =>
//      Address(lines.init, lines.last)
//    }
//  }


  def genEoriCheckResponse(checkRequest: CheckRequest): Gen[Option[CheckResponse]] = {
   val requestedEori = checkRequest
    val isValid = requestedEori
    for {
      isValid <- Gen.boolean
      traderName <- if(isValid) genTraderName.sometimes else Gen.const(None)
      address <- if(isValid) genAddress.sometimes else Gen.const(None)
    } yield CheckResponse(
      requestedEori,
      isValid,
      traderName,
      address
    ).some
  }

}
