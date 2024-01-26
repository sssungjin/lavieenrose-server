package org.dongguk.lavieenrosehotel.constraint

object Constants {
    const val USER_ID_CLAIM_NAME = "uid"
    const val USER_ROLE_CLAIM_NAME = "rol"

    val NO_NEED_AUTH_URLS = arrayOf(
        "/auth/sign-up",
        "/auth/reissue",
        "/oauth2/authorization/kakao",
        "/oauth2/authorization/google",
        "/login/oauth2/code/kakao",
        "/login/oauth2/code/google",

        "/api-docs.html",
        "/api-docs/**",
        "/swagger-ui/**"
    )

    val ALL_URLS = arrayOf(
        "/auth/reissue",
        "/auth/sign-out",
    )

    val ADMIN_URLS = arrayOf(
        "/admin/**"
    )

    val USER_URLS = arrayOf(
        "/**"
    )

    val GUEST_URLS = arrayOf(
        "/oauth2/sign-up",
    )
}