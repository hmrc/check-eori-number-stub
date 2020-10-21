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

package uk.gov.hmrc.checkeorinumberstub.controllers

import javax.inject.{Inject, Singleton}
import play.api.libs.json.Json
import play.api.mvc.ControllerComponents
import uk.gov.hmrc.checkeorinumberstub.config.AppConfig
import uk.gov.hmrc.checkeorinumberstub.models._
import uk.gov.hmrc.checkeorinumberstub.services.EisGenerator
import uk.gov.hmrc.play.bootstrap.backend.controller.BackendController

import scala.concurrent.Future

@Singleton()
class EisController @Inject()(
  appConfig: AppConfig,
  cc: ControllerComponents
) extends BackendController(cc) {


  def eoriChecker() = {
    Action.async(parse.json) { implicit request =>
      withJsonBody[CheckRequest](checkRequest => {
        //    val r: CheckResponse = EisGenerator
        //        .genDstRegisterResponse.map(_.response)
        //        .sample.get
        //    Ok(Json.toJson("hey"))
        println(s"AAAAAAAAAAAAA${Json.toJson(checkRequest)}")
        Future.successful(Ok(
          Json.toJson(
            CheckResponse(
              checkRequest.eoriNumbers.head,
              valid = true,
              Some("Hay's Limonard"),
              Some(
                Address(
                  "House 1",
                  "City 2",
                  "AA111AA"
                )
              )
            )
          )
        ))
      }
      )
    }
  }
}