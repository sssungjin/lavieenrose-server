package org.dongguk.lavieenrosehotel.domain

import jakarta.persistence.*
import lombok.AccessLevel
import lombok.NoArgsConstructor
import org.dongguk.lavieenrosehotel.dto.request.ReservationAmenityDto
import org.dongguk.lavieenrosehotel.dto.type.EAmenityType
import org.dongguk.lavieenrosehotel.dto.type.factory.EAmenityTypeFactory
import java.time.LocalDate

@Entity
@Table(name = "reservation_amenities")
@DiscriminatorValue("AMENITY")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class ReservationAmenity(
    /* Column Mapping */
    @Column(name = "amenity_type") @Enumerated(EnumType.STRING)
    private var _amenityType: EAmenityType,

    @Column(name = "apply_date")
    private var _startDate: LocalDate,

    @Column(name = "adult_cnt")
    private var _adultCnt: Int,

    @Column(name = "teenager_cnt")
    private var _teenagerCnt: Int,

    @Column(name = "child_cnt")
    private var _childCnt: Int,

    /* Relation Parent Mapping */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Amenity_id")
    private var _amenity: Amenity,

    // Reservation Constructor
    _reservationName: String,
    _reservationPhoneNumber: String,
    _price: Int,
    _discountPrice: Int,
    _paymentPrice: Int,
    _user: User
) : Reservation(
        _reservationName = _reservationName,
        _reservationPhoneNumber = _reservationPhoneNumber,
        _price = _price,
        _discountPrice = _discountPrice,
        _paymentPrice = _paymentPrice,
        _user = _user
){
    val amenityType: EAmenityType
        get() = _amenityType
    val startDate: LocalDate
        get() = _startDate
    val adultCnt: Int
        get() = _adultCnt
    val teenagerCnt: Int
        get() = _teenagerCnt
    val childCnt: Int
        get() = _childCnt
    val amenity: Amenity
        get() = _amenity

    companion object {
        fun fromJson(requestBody: ReservationAmenityDto, user: User, amenity: Amenity): ReservationAmenity {
            return ReservationAmenity(
                _amenityType = EAmenityTypeFactory.of(requestBody.amenityType.toString()),
                _startDate = requestBody.startDate,
                _adultCnt = requestBody.adultCnt,
                _teenagerCnt = requestBody.teenagerCnt,
                _childCnt = requestBody.childCnt,
                _reservationName = requestBody.reservationName,
                _reservationPhoneNumber = requestBody.reservationPhoneNumber,
                _price = requestBody.price,
                _discountPrice = requestBody.discountPrice,
                _paymentPrice = requestBody.paymentPrice,
                _user = user,
                _amenity = amenity
            )
        }
    }
}