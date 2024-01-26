package org.dongguk.lavieenrosehotel.dto.type

enum class ERole(val value: String, val databaseValue: String) {
    ADMIN("ROLE_ADMIN", "ADMIN"),
    USER("ROLE_USER", "USER"),
    GUEST("ROLE_GUEST", "GUEST")
}