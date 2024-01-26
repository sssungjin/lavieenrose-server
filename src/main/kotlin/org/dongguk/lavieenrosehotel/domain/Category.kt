package org.dongguk.lavieenrosehotel.domain

import jakarta.persistence.*
import lombok.AccessLevel
import lombok.NoArgsConstructor
import org.dongguk.lavieenrosehotel.dto.request.CategoryDto
import org.dongguk.lavieenrosehotel.dto.request.StatusDto
import org.dongguk.lavieenrosehotel.dto.type.ERoomType
import org.dongguk.lavieenrosehotel.dto.type.EViewType
import org.dongguk.lavieenrosehotel.dto.type.EVisibleStatus

@Entity
@Table(name = "categories")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class Category(
    /* Column Mapping */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private var _id: Long? = null,

    @Column(name = "name")
    private var _name: String,

    @Column(name = "room_type") @Enumerated(EnumType.STRING)
    private var _roomType: ERoomType,

    @Column(name = "view_type") @Enumerated(EnumType.STRING)
    private var _viewType: EViewType,

    @Column(name = "summary")
    private var _summary: String,

    @Column(name = "standard_capacity")
    private var _standardCapacity: Int,

    @Column(name = "max_capacity")
    private var _maxCapacity: Int,

    @Column(name = "default_price")
    private var _defaultPrice: Int,

    @Column(name = "peek_price")
    private var _peekPrice: Int,

    @Column(name = "addition_price")
    private var _additionPrice: Int,

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private var _status: EVisibleStatus,

    /* Relation Parent Mapping */
    @OneToMany(mappedBy = "_category", cascade = [CascadeType.PERSIST], fetch = FetchType.LAZY)
    private var _rooms: MutableList<Room> = mutableListOf(),

    @OneToMany(mappedBy = "_category", cascade = [CascadeType.PERSIST], fetch = FetchType.LAZY)
    private var _reservationRooms: MutableList<ReservationRoom> = mutableListOf(),

    @OneToMany(mappedBy = "_category", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    private var _image: MutableList<Image> = mutableListOf(),
) {
    val id: Long?
        get() = _id
    val name: String
        get() = _name
    val roomType: ERoomType
        get() = _roomType
    val viewType: EViewType
        get() = _viewType
    val summary: String
        get() = _summary
    val standardCapacity: Int
        get() = _standardCapacity
    val maxCapacity: Int
        get() = _maxCapacity
    val defaultPrice: Int
        get() = _defaultPrice
    val peekPrice: Int
        get() = _peekPrice
    val additionPrice: Int
        get() = _additionPrice
    val status: EVisibleStatus
        get() = _status
    val rooms: List<Room>
        get() = _rooms
    val reservationRooms: List<ReservationRoom>
        get() = _reservationRooms
    val image: List<Image>
        get() = _image



    companion object {
        fun fromJson(
            categoryDto: CategoryDto
        ): Category {
            return Category(
                _name = categoryDto.name,
                _roomType = categoryDto.roomType,
                _viewType = categoryDto.viewType,
                _summary = categoryDto.summary,
                _standardCapacity = categoryDto.standardCapacity,
                _maxCapacity = categoryDto.maxCapacity,
                _defaultPrice = categoryDto.defaultPrice,
                _peekPrice = categoryDto.peekPrice,
                _additionPrice = categoryDto.additionPrice,
                _status = categoryDto.status,
            )
        }
    }

    fun update(
        categoryDto: CategoryDto
    ) {
        _name = categoryDto.name
        _roomType = categoryDto.roomType
        _viewType = categoryDto.viewType
        _summary = categoryDto.summary
        _standardCapacity = categoryDto.standardCapacity
        _maxCapacity = categoryDto.maxCapacity
        _defaultPrice = categoryDto.defaultPrice
        _peekPrice = categoryDto.peekPrice
        _additionPrice = categoryDto.additionPrice
        _status = categoryDto.status
    }

    fun updateStatus(requestBody: StatusDto) {
        _status = requestBody.status
    }
}