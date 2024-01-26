package org.dongguk.lavieenrosehotel.domain

import jakarta.persistence.*
import lombok.AccessLevel
import lombok.NoArgsConstructor
import org.dongguk.lavieenrosehotel.dto.type.EReservationStatus
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.SQLDelete

@Entity
@Table(name = "reservations")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="DTYPE")
@SQLDelete(sql = "UPDATE reservations SET status = 'CANCEL' WHERE id = ?")
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
open class Reservation(
    /* Column Mapping */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected open var _id: Long? = null,

    @Column(name = "user_name")
    protected open var _reservationName: String,

    @Column(name = "user_phone_number")
    protected open var _reservationPhoneNumber: String,

    @Column(name = "status") @Enumerated(EnumType.STRING)
    protected open var _status: EReservationStatus = EReservationStatus.RESERVATION,

    @Column(name = "price")
    protected open var _price: Int,

    @Column(name = "discount_price")
    protected open var _discountPrice: Int,

    @Column(name = "payment_price")
    protected open var _paymentPrice: Int,

    /* Relation Parent Mapping */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    protected open var _user: User,

    /* Relation Child Mapping */
    @OneToOne(mappedBy = "_reservation", cascade = [CascadeType.PERSIST], fetch = FetchType.LAZY)
    protected open var _paymentHistories: PaymentHistory? = null,
) {
    val id: Long?
        get() = _id
    val reservationName: String
        get() = _reservationName
    val reservationPhoneNumber: String
        get() = _reservationPhoneNumber
    val status: EReservationStatus
        get() = _status
    val price: Int
        get() = _price
    val discountPrice: Int
        get() = _discountPrice
    val paymentPrice: Int
        get() = _paymentPrice
    val user: User
        get() = _user
    val paymentHistories: PaymentHistory?
        get() = _paymentHistories

    constructor(
        reservationName: String,
        reservationPhoneNumber: String,
        price: Int,
        discountPrice: Int,
        paymentPrice: Int,
        user: User,
    ): this(
        _reservationName = reservationName,
        _reservationPhoneNumber = reservationPhoneNumber,
        _price = price,
        _discountPrice = discountPrice,
        _paymentPrice = paymentPrice,
        _user = user,
    )

    fun updateStatus(status: EReservationStatus) {
        _status = status
    }
}