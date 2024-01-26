package org.dongguk.lavieenrosehotel.domain

import jakarta.persistence.*
import lombok.AccessLevel
import lombok.NoArgsConstructor
import org.dongguk.lavieenrosehotel.dto.request.AuthSignUpDto
import org.dongguk.lavieenrosehotel.dto.type.EProvider
import org.dongguk.lavieenrosehotel.dto.type.ERole
import org.dongguk.lavieenrosehotel.dto.type.factory.EProviderFactory
import org.dongguk.lavieenrosehotel.dto.type.factory.ERoleFactory
import java.time.LocalDate

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class User(
    /* Column Mapping */
    /* User Default Value */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private var _id: Long? = null,

    @Column(name = "serial_id")
    private var _serialId: String,

    @Column(name = "password")
    private var _password: String,

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private var _role: ERole,

    @Column(name = "provider")
    @Enumerated(EnumType.STRING)
    private var _provider: EProvider,

    @Column(name = "created_date")
    private var _createdDate: LocalDate? = null,

    /* User Custom Value */
    @Column(name = "name")
    private var _name: String? = null,
    @Column(name = "phone_number")
    private var _phoneNumber: String? = null,

    /* User Auth Value */
    @Column(name = "refresh_token")
    private var _refreshToken: String? = null,
    @Column(name = "is_login")
    private var _isLogin: Boolean,

    /* Relation Parent Mapping */
    @OneToMany(mappedBy = "_user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    private var _reservation: MutableList<Reservation> = mutableListOf(),

    @OneToMany(mappedBy = "_user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    private var _paymentHistories: MutableList<PaymentHistory> = mutableListOf(),

    @OneToMany(mappedBy = "_user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    private var _pointHistories: MutableList<PointHistory> = mutableListOf(),

    ) {
    /* Getter */
    /* Default Column */
    val id: Long?
        get() = _id
    val serialId: String
        get() = _serialId
    val password: String
        get() = _password
    val role: ERole
        get() = _role
    val provider: EProvider
        get() = _provider
    val createdDate: LocalDate?
        get() = _createdDate
    val name: String?
        get() = _name
    val phoneNumber: String?
        get() = _phoneNumber
    val refreshToken: String?
        get() = _refreshToken
    val isLogin: Boolean
        get() = _isLogin

    /* Relation Parent Mapping */
    val reservation: List<Reservation>
        get() = _reservation
    val paymentHistory: List<PaymentHistory>
        get() = _paymentHistories
    val pointHistory: List<PointHistory>
        get() = _pointHistories

    constructor(
        _serialId: String,
        _password: String,
        _role: ERole,
        _provider: EProvider,
    ) : this(
        _serialId = _serialId,
        _password = _password,
        _role = _role,
        _provider = _provider,
        _createdDate = LocalDate.now(),
        _isLogin = true,
    )

    /* Setter */
    fun register(name : String, phoneNumber : String) {
        _name = name
        _phoneNumber = phoneNumber
        _role = ERoleFactory.of("USER")
    }

    fun updatePassword(password : String) {
        _password = password
    }

    fun updateRefreshToken(refreshToken : String) {
        _refreshToken = refreshToken
    }

    fun updateLogin(isLogin : Boolean) {
        _isLogin = isLogin
    }

    companion object {
        fun fromSignUpJson(authSignUpDto: AuthSignUpDto, password: String): User {
            return User(
                _serialId = authSignUpDto.serialId,
                _password = password,
                _role = ERoleFactory.of("GUEST"),
                _provider = EProviderFactory.of("DEFAULT"),
            ).apply {
                register(authSignUpDto.name, authSignUpDto.phoneNumber)
            }
        }
    }
}