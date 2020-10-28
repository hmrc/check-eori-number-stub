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
import uk.gov.hmrc.checkeorinumberstub.models.{Address, CheckMultipleEoriNumbersRequest, CheckResponse, EoriNumber}


object EisGenerator {

  //TODO Check is this Gen will be best for TraderName after research
  //   lazy val genFirstAndLastName: Gen[String] = {
  //    for {
  //      fname <- Gen.forename
  //      sname <- Gen.surname
  //    } yield s"$fname $sname"
  //  }

  private def genAddress(isValid: Boolean, eoriLastDigit: Int): Gen[Option[Address]] = {
    val shouldGen = isValid && (eoriLastDigit == 2 || eoriLastDigit == 3 || eoriLastDigit == 6 || eoriLastDigit == 7)
    shouldGen match {
      case true =>
        for {
          a <- Gen.some(Gen.oneOf("50 George Street", "10 Park Avenue", "12 Baker Street"))
          b <- Gen.some(Gen.oneOf("Brighton", "Liverpool", "London"))
          c  <- Gen.some(Gen.oneOf("BN11 5HA", "L1 5QT", "SS13 4FT"))
        } yield Address(a.getOrElse(""), b.getOrElse(""), c.getOrElse("")).some
      case false => Gen.const(None)
    }
  }

//  private lazy val addressGen: Gen[Address] = {
//    Gen.ukAddress.map { lines =>
//      Address(lines.init, lines.last)
//    }
//  }


  private def genTraderName(isValid: Boolean, eoriLastDigit: Int): Gen[Option[String]] = {
    if(isValid && eoriLastDigit >= 2 && eoriLastDigit <= 5)
      Gen.some(Gen.company.retryUntil(a => a.length < 35 && a.length > 1))
    else
      Gen.const(None)
  }

  private def isValidEori(eoriNumber: EoriNumber): Gen[Boolean] = {
    eoriNumber match {
      case en if en.last.asDigit >= 0 && en.last.asDigit <= 7 => Gen.const(true)
      case _ => Gen.const(false)
    }
  }

  /*
   The last digit of each EoriNumber will return each type of CheckResponse =>
    0 || 1  Vaild with no TraderName or Address
    2 || 3  Vaild with TraderName and Address
    4 || 5  Vaild with TraderName
    6 || 7  Vaild with Address
    8 || 9  Invalid
   */

  def genEoriCheckResponse(checkRequest: CheckMultipleEoriNumbersRequest): Gen[List[CheckResponse]] = {
    val ret = checkRequest.eoriNumbers.map { requestedEori =>
      val eoriLastDigit = requestedEori.last.asDigit
      for {
        isValid <- isValidEori(requestedEori)
        tn <- genTraderName(isValid, eoriLastDigit)
        address <- genAddress(isValid, eoriLastDigit)
      } yield CheckResponse(
        requestedEori,
        isValid,
        tn,
        address
      )
    }
    ret.sequence
  }

}
