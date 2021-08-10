package com.dls.accountservicecommand.adapter.`in`.controller.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
class CreateAccountRequest(
    @field:NotBlank(message = "field required")
    val customerId: String,
    @field:Min(value=0, message="must be positive")
    val balance: Double,
)