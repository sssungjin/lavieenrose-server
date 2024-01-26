package org.dongguk.lavieenrosehotel.domain

import jakarta.persistence.*
import lombok.AccessLevel
import lombok.NoArgsConstructor
import org.dongguk.lavieenrosehotel.dto.request.AmenityDto
import org.dongguk.lavieenrosehotel.dto.type.EAmenityType
import org.hibernate.annotations.DynamicUpdate

@Entity
@Table(name = "amenities")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
class Amenity(
    /* Column Mapping */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private var _id: Long? = null,

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private var _type: EAmenityType,

    @Column(name = "name")
    private var _name: String,

    @Column(name = "summary")
    private var _summary: String,

    @Column(name = "information")
    private var _information: String,

    @Column(name = "all_day_price")
    private var _allDayPrice: Int,

    /* Relation Parent Mapping */
    @OneToMany(mappedBy = "_amenity", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    private var _reservationAmenities: MutableList<ReservationAmenity> = mutableListOf(),

    @OneToMany(mappedBy = "_amenity", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    private var _images: MutableList<Image> = mutableListOf(),
) {
    val id: Long?
        get() = _id
    val type: EAmenityType
        get() = _type
    val name: String
        get() = _name
    val summary: String
        get() = _summary
    val information: String
        get() = _information
    val allDayPrice: Int
        get() = _allDayPrice
    val images: List<Image>
        get() = _images

    fun update(requestBody: AmenityDto) {
        _type = requestBody.amenityType
        _name = requestBody.name
        _summary = requestBody.summary
        _information = requestBody.information
        _allDayPrice = requestBody.allDayPrice
    }

    companion object {
        fun fromJson(requestBody: AmenityDto): Amenity {
            return Amenity(
                _type = requestBody.amenityType,
                _name = requestBody.name,
                _summary = requestBody.summary,
                _information = requestBody.information,
                _allDayPrice = requestBody.allDayPrice,
            )
        }
    }
}

