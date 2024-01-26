package org.dongguk.lavieenrosehotel.security.service

import org.dongguk.lavieenrosehotel.domain.User
import org.dongguk.lavieenrosehotel.dto.type.EProvider
import org.dongguk.lavieenrosehotel.dto.type.ERole
import org.dongguk.lavieenrosehotel.dto.type.factory.EProviderFactory
import org.dongguk.lavieenrosehotel.repository.UserRepository
import org.dongguk.lavieenrosehotel.security.info.UserPrincipal
import org.dongguk.lavieenrosehotel.security.info.factory.OAuth2UserInfoFactory.getOAuth2UserInfo
import org.dongguk.lavieenrosehotel.utility.PasswordUtil
import org.springframework.security.authentication.InternalAuthenticationServiceException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import java.util.*


@Service
class CustomOAuth2UserService(
    private val userRepository: UserRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
): DefaultOAuth2UserService() {
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        return try {
            process(userRequest, super.loadUser(userRequest))
        } catch (ex: Exception) {
            ex.printStackTrace()
            throw ex
        }
    }

    fun process(userRequest: OAuth2UserRequest, oauth2User: OAuth2User): OAuth2User {
        val provider: EProvider =
            EProviderFactory.of(userRequest.clientRegistration.registrationId.uppercase(Locale.getDefault()))
        val userInfo = getOAuth2UserInfo(provider, oauth2User.attributes)

        val user: UserRepository.UserSecurityForm = userRepository.findFormBySerialIdAndProvider(userInfo.id, provider)
            .orElseGet {
                userRepository.save(
                    User(
                        _serialId = userInfo.id,
                        _password = bCryptPasswordEncoder.encode(PasswordUtil.generateRandomPassword()),
                        _provider = provider,
                        _role = ERole.GUEST,
                    )
                ).let {
                    UserRepository.UserSecurityForm.invoke(it)
                }
            }

        return UserPrincipal.create(user, oauth2User.attributes)
    }
}