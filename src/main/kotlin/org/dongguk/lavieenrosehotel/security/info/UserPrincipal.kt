package org.dongguk.lavieenrosehotel.security.info

import org.dongguk.lavieenrosehotel.domain.User
import org.dongguk.lavieenrosehotel.dto.type.EProvider
import org.dongguk.lavieenrosehotel.dto.type.ERole
import org.dongguk.lavieenrosehotel.repository.UserRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.core.oidc.OidcIdToken
import org.springframework.security.oauth2.core.oidc.OidcUserInfo
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.security.oauth2.core.user.OAuth2User
import java.util.*

class UserPrincipal(
    val id: Long,
    val role: ERole,
    private val password: String,
    private val authorities: Collection<GrantedAuthority?>,
    private val attributes: MutableMap<String, Any>,
) : OAuth2User, OidcUser, UserDetails {
    companion object {
        fun create(
            userForm: UserRepository.UserSecurityForm,
            attributes: MutableMap<String, Any>, ): UserPrincipal {
            return UserPrincipal(
                id = userForm.id,
                role = userForm.role,
                password = userForm.password,
                authorities = Collections.singleton(SimpleGrantedAuthority(userForm.role.value)),
                attributes = attributes,
            )
        }
    }

    /*
     * OAuth2User
     */
    override fun getAuthorities(): Collection<GrantedAuthority?> {
        return authorities
    }

    override fun getName(): String {
        return id.toString()
    }

    override fun getAttributes(): Map<String, Any> {
        return attributes
    }

    /*
     * OidcUser
     */
    override fun getClaims(): Map<String, Any>? {
        return null
    }

    override fun getUserInfo(): OidcUserInfo? {
        return null
    }

    override fun getIdToken(): OidcIdToken? {
        return null
    }

    /*
     * UserDetails
     */
    override fun getUsername(): String {
        return id.toString()
    }

    override fun getPassword(): String {
        return password
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}