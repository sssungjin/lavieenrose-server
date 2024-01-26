package org.dongguk.lavieenrosehotel.constraint

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.dongguk.lavieenrosehotel.annotation.Date
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DateValidator: ConstraintValidator<Date, String> {
    private val DATE_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    override fun initialize(constraintAnnotation: Date?) {
        super.initialize(constraintAnnotation)
    }

    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        if (value.isNullOrEmpty()) return false

        try {
            LocalDate.parse(value, DATE_FORMATTER)
        } catch (e: Exception) {
            return false
        }

        return true
    }
}