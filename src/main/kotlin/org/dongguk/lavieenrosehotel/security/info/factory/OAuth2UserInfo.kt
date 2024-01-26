package org.dongguk.lavieenrosehotel.security.info.factory

abstract class OAuth2UserInfo(
    protected val attributes: Map<String, Any>
) {
    abstract val id: String
}