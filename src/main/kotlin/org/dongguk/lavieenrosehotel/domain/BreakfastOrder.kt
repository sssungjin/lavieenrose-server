package org.dongguk.lavieenrosehotel.domain

import jakarta.persistence.*
import lombok.AccessLevel
import lombok.NoArgsConstructor
import org.dongguk.lavieenrosehotel.dto.request.ReservationRoomDto
import java.time.LocalDate

@Entity
@Table(name = "breakfast_orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class BreakfastOrder(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private var _id: Long? = null,

    @Column(name = "apply_date")
    private var _applyDate: LocalDate,

    @Column(name = "is_eating")
    private var _isEating: Boolean,

    /* Relation Parent Mapping */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private var _reservationRoom: ReservationRoom,


) {
    val id: Long?
        get() = _id

    val applyDate: LocalDate
        get() = _applyDate

    val isEating: Boolean
        get() = _isEating

    val reservationRoom: ReservationRoom
        get() = _reservationRoom

    companion object {
        fun createOrder(applyDate: LocalDate, isEating: Boolean, reservationRoom: ReservationRoom): BreakfastOrder {
            return BreakfastOrder(
                _applyDate = applyDate,
                _isEating = isEating,
                _reservationRoom = reservationRoom
            )
        }
    }
}