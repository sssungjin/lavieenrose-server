package org.dongguk.lavieenrosehotel.domain

import jakarta.persistence.*
import lombok.AccessLevel
import lombok.NoArgsConstructor
import org.dongguk.lavieenrosehotel.dto.request.ReservationTransportationDto
import org.dongguk.lavieenrosehotel.dto.type.ETransportationType
import org.dongguk.lavieenrosehotel.dto.type.factory.ETransportationTypeFactory
import java.time.LocalDate

@Entity
@Table(name = "reservation_transportations")
@DiscriminatorValue("TRANSPORTATION")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class ReservationTransportation(
    @Column(name = "transportation_type") @Enumerated(EnumType.STRING)
    private var _type: ETransportationType,

    @Column(name = "location_name")
    private var _locationName: String,

    @Column(name = "start_date")
    private var _startDate: LocalDate,

    @Column(name = "end_date")
    private var _endDate: LocalDate,

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
    ) {
    val type: ETransportationType
        get() = _type
    val locationName: String
        get() = _locationName
    val startDate: LocalDate
        get() = _startDate
    val endDate: LocalDate
        get() = _endDate

    companion object {
        fun fromJson(requestBody: ReservationTransportationDto, user: User): ReservationTransportation {
            return ReservationTransportation(
                _type = ETransportationTypeFactory.of(requestBody.typeId),
                _locationName = requestBody.locationName,
                _startDate = requestBody.startDate,
                _endDate = requestBody.endDate,
                _reservationName = requestBody.reservationName,
                _reservationPhoneNumber = requestBody.reservationPhoneNumber,
                _price = requestBody.price,
                _discountPrice = requestBody.discountPrice,
                _paymentPrice = requestBody.paymentPrice,
                _user = user
            )
        }
    }
}