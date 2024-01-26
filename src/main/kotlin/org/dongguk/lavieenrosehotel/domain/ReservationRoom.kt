package org.dongguk.lavieenrosehotel.domain

import jakarta.persistence.*
import lombok.AccessLevel
import lombok.NoArgsConstructor
import org.dongguk.lavieenrosehotel.dto.request.ReservationRoomDto
import java.time.LocalDate

@Entity
@Table(name = "reservation_rooms")
@DiscriminatorValue("ROOM")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class ReservationRoom(
    @Column(name = "start_date")
    private var _startDate: LocalDate,

    @Column(name = "end_date")
    private var _endDate: LocalDate,

    @Column(name = "adult_cnt")
    private var _adultCnt: Int,

    @Column(name = "teenager_cnt")
    private var _teenagerCnt: Int,

    @Column(name = "child_cnt")
    private var _childCnt: Int,

    /* Relation Child Mapping */
    @OneToMany(mappedBy = "_reservationRoom", cascade = [CascadeType.ALL])
    private var _breakfastOrders: MutableList<BreakfastOrder> = mutableListOf(),

    /* Relation Parent Mapping */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private var _room: Room,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private var _category: Category,

    // Reservation Constructor
    _reservationName: String,
    _reservationPhoneNumber: String,
    _price: Int,
    _discountPrice: Int,
    _paymentPrice: Int,
    _user: User
): Reservation(
    _reservationName = _reservationName,
    _reservationPhoneNumber = _reservationPhoneNumber,
    _price = _price,
    _discountPrice = _discountPrice,
    _paymentPrice = _paymentPrice,
    _user = _user
) {
    val startDate: LocalDate
        get() = _startDate
    val endDate: LocalDate
        get() = _endDate
    val adultCnt: Int
        get() = _adultCnt
    val teenagerCnt: Int
        get() = _teenagerCnt
    val childCnt: Int
        get() = _childCnt
    val breakfastOrders: MutableList<BreakfastOrder>
        get() = _breakfastOrders
    val room: Room
        get() = _room
    val category: Category
        get() = _category

    companion object {
        fun fromJson(requestBody: ReservationRoomDto, user: User, room: Room, category: Category): ReservationRoom {
            return ReservationRoom(
                _startDate = requestBody.startDate,
                _endDate = requestBody.endDate,
                _adultCnt = requestBody.adultCnt,
                _teenagerCnt = requestBody.teenagerCnt,
                _childCnt = requestBody.childCnt,
                _reservationName = requestBody.reservationName,
                _reservationPhoneNumber = requestBody.reservationPhoneNumber,
                _price = requestBody.price,
                _discountPrice = requestBody.discountPrice,
                _paymentPrice = requestBody.paymentPrice,
                _user = user,
                _room = room,
                _category = category
            )
        }
    }
}