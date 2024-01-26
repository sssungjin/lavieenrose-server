package org.dongguk.lavieenrosehotel.domain

import jakarta.persistence.*
import lombok.AccessLevel
import lombok.NoArgsConstructor
import org.dongguk.lavieenrosehotel.dto.type.EPaymentMethod
import org.dongguk.lavieenrosehotel.event.ReservationEvent
import org.hibernate.annotations.DynamicUpdate
import java.time.LocalDate

@Entity
@Table(name = "payment_histories")
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class PaymentHistory(
    /* Column Mapping */
    @Id
    @Column(name = "payment_uid")
    private var _paymentUid: String,

    @Column(name = "is_refund")
    private var _isRefund: Boolean,

    @Column(name = "payment_method")
    @Enumerated(EnumType.STRING)
    private var _paymentMethod: EPaymentMethod,

    @Column(name = "payment_date")
    private var _paymentDate: LocalDate,

    @Column(name = "payment_price")
    private var _paymentPrice: Int,

    @Column(name = "refund_date")
    private var _refundDate: LocalDate? = null,

    @Column(name = "refund_price")
    private var _refundPrice: Int? = null,

    /* Relation Parent Mapping */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private var _reservation: Reservation,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private var _user: User,
) {
    val paymentUid: String
        get() = _paymentUid
    val isRefund: Boolean
        get() = _isRefund
    val paymentMethod: EPaymentMethod
        get() = _paymentMethod
    val paymentDate: LocalDate
        get() = _paymentDate
    val paymentPrice: Int
        get() = _paymentPrice
    val refundDate: LocalDate?
        get() = _refundDate
    val refundPrice: Int?
        get() = _refundPrice
    val reservation: Reservation
        get() = _reservation
    val user: User
        get() = _user

    fun refund(refundPrice: Int) {
        _isRefund = true
        _refundDate = LocalDate.now()
        _refundPrice = refundPrice
    }

    companion object {
        fun fromEvent(
            event: ReservationEvent,
            reservation: Reservation,
            user: User): PaymentHistory {
            return PaymentHistory(
                _paymentUid = event.impUid,
                _isRefund = event.isRefund,
                _paymentMethod = event.method,
                _paymentDate = LocalDate.now(),
                _paymentPrice = event.price,
                _reservation = reservation,
                _user = user,
            )
        }
    }
}