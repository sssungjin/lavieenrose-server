package org.dongguk.lavieenrosehotel.repository

import org.dongguk.lavieenrosehotel.domain.User
import org.dongguk.lavieenrosehotel.dto.type.EProvider
import org.dongguk.lavieenrosehotel.dto.type.ERole
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UserRepository: JpaRepository<User, Long> {

    @Query("SELECT u._id AS id, u._role AS role, u._password AS password FROM User u " +
            "WHERE u._id = :id " +
            "AND u._isLogin = true")
    fun findFormById(id: Long): Optional<UserSecurityForm>

    @Query("SELECT u._id AS id, u._role AS role, u._password AS password FROM User u " +
            "WHERE u._serialId = :serialId " +
            "AND u._provider = :provider")
    fun findFormBySerialIdAndProvider(serialId: String, provider: EProvider): Optional<UserSecurityForm>

    @Modifying(clearAutomatically = true)
    @Query("UPDATE User u " +
            "SET u._refreshToken = :refreshToken, u._isLogin = :isLogin " +
            "WHERE u._id = :id")
    fun updateRefreshTokenAndLoginStatus(id: Long, refreshToken: String, isLogin: Boolean)

    fun findBy_serialId(serialId: String): Optional<User>

    /* Security 용 */
    interface UserSecurityForm {
        val id: Long
        val role: ERole
        val password: String

        companion object {
            operator fun invoke(user: User): UserSecurityForm {
                return object: UserSecurityForm {
                    override val id: Long = user.id!!
                    override val role: ERole = user.role
                    override val password: String = user.password
                }
            }
        }
    }
}