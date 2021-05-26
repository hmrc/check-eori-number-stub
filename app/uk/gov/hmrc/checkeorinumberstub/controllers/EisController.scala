/*
 * Copyright 2021 HM Revenue & Customs
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

import play.api.libs.json._
import play.api.mvc.{Action, ControllerComponents}
import uk.gov.hmrc.checkeorinumberstub.config.AppConfig
import uk.gov.hmrc.checkeorinumberstub.models._
import uk.gov.hmrc.checkeorinumberstub.services.EisService
import uk.gov.hmrc.play.bootstrap.backend.controller.BackendController

import scala.concurrent.Future

@Singleton()
class EisController @Inject()(
  appConfig: AppConfig,
  cc: ControllerComponents,
  eisService: EisService
) extends BackendController(cc) {

  def eoriChecker(): Action[JsValue] = {
    Action.async(parse.json) { implicit request =>
      withJsonBody[CheckMultipleEoriNumbersRequest](checkRequest => {
        Future.successful(
          Ok(
            Json.obj("party" -> JsArray(
              eisService.handleEoriCheckRequest(checkRequest).map{ r =>
                Json.obj("identifications" -> r)
              })
            )
          )
        )
      })
    }
  }
}