package org.dongguk.lavieenrosehotel.security.info.factory

import org.dongguk.lavieenrosehotel.dto.type.EProvider
import org.dongguk.lavieenrosehotel.exception.CommonException
import org.dongguk.lavieenrosehotel.exception.ErrorCode
import org.dongguk.lavieenrosehotel.security.info.GoogleOAuth2UserInfo
import org.dongguk.lavieenrosehotel.security.info.KakaoOAuth2UserInfo


object OAuth2UserInfoFactory {
    fun getOAuth2UserInfo(provider: EProvider, attributes: Map<String, Any>): OAuth2UserInfo {
        return when (provider) {
            EProvider.GOOGLE -> GoogleOAuth2UserInfo(attributes)
            EProvider.KAKAO -> KakaoOAuth2UserInfo(attributes)
            else -> throw CommonException(ErrorCode.BAD_REQUEST)
        }
    }
}