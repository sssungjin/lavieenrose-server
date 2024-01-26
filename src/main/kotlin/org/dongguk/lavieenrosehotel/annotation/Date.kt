package org.dongguk.lavieenrosehotel.annotation

import jakarta.validation.Constraint
import jakarta.validation.Payload
import org.dongguk.lavieenrosehotel.constraint.DateValidator
import kotlin.reflect.KClass


@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [DateValidator::class])
@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
annotation class Date(
    val message: String = "Invalid Date Format. (yyyy-MM-dd)",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)