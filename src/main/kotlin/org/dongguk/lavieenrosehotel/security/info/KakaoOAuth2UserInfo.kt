package org.dongguk.lavieenrosehotel.security.info

import org.dongguk.lavieenrosehotel.security.info.factory.OAuth2UserInfo

class KakaoOAuth2UserInfo(
    attributes: Map<String, Any>
): OAuth2UserInfo(attributes) {
    override val id: String
        get() = attributes["id"].toString()
}