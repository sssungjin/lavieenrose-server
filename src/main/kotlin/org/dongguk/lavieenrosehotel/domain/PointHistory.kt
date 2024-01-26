package org.dongguk.lavieenrosehotel.domain

import jakarta.persistence.*
import lombok.AccessLevel
import lombok.NoArgsConstructor
import java.time.LocalDate

@Entity
@Table(name = "point_histories")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class PointHistory(
    /* Column Mapping */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private var _id: Long? = null,

    @Column(name = "is_using")
    private var _isUsing: Boolean,

    @Column(name = "point")
    private var _point: Int,

    @Column(name = "issue_date")
    private var _issueDate: LocalDate,

    @Column(name = "expiry_date")
    private var _expiryDate: LocalDate,

    @Column(name = "reason", length = 100)
    private var _reason: String,

    /* Relation Parent Mapping */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private var _user: User,
) {
    val id: Long?
        get() = _id
    val isUsing: Boolean
        get() = _isUsing
    val point: Int
        get() = _point
    val issueDate: LocalDate
        get() = _issueDate
    val expiryDate: LocalDate
        get() = _expiryDate
    val reason: String
        get() = _reason
    val user: User
        get() = _user
}