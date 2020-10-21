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

package uk.gov.hmrc.checkeorinumberstub.models

import play.api.libs.json._

case class CheckRequest(
  eoriNumbers: List[EoriNumber]
)

object CheckRequest {
  implicit val checkRequestFormat: OFormat[CheckRequest] = Json.format[CheckRequest]
//
//  implicit val eoriListWriter: Writes[List[EoriNumber]] = new Writes[List[EoriNumber]] {
//    override def writes(dt: List[EoriNumber]): JsValue = {
//      Json.obj("eoris" -> Json.toJson(dt))
//    }
//  }

}