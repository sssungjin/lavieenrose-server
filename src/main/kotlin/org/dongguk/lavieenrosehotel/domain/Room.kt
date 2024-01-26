package org.dongguk.lavieenrosehotel.domain

import jakarta.persistence.*
import lombok.NoArgsConstructor
import org.dongguk.lavieenrosehotel.dto.request.RoomDto
import org.dongguk.lavieenrosehotel.dto.request.StatusDto
import org.dongguk.lavieenrosehotel.dto.type.EBedType
import org.dongguk.lavieenrosehotel.dto.type.EVisibleStatus

@Entity
@Table(name = "rooms")
@NoArgsConstructor
class Room(
    /* Column Mapping */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private var _id: Long? = null,

    @Column(name = "floor")
    private var _floor: Int,

    @Column(name = "room_number")
    private var _roomNumber: Int,

    @Column(name = "size")
    private var _size: Double,

    @Column(name = "status") @Enumerated(EnumType.STRING)
    private var _status: EVisibleStatus,

    @Column(name = "bedroom_cnt")
    private var _bedroomCnt: Int,

    @Column(name = "bathroom_cnt")
    private var _bathroomCnt: Int,

    @Column(name = "living_room_cnt")
    private var _livingRoomCnt: Int,

    @Column(name = "kitchen_cnt")
    private var _kitchenCnt: Int,

    @Column(name = "terrace_cnt")
    private var _terraceCnt: Int,

    @Column(name = "bed_type") @Enumerated(EnumType.STRING)
    private var _bedType: EBedType,

    @Column(name = "bed_cnt")
    private var _bedCnt: Int,

    /* Relation Mapping */
    @OneToOne(mappedBy = "_room", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    private var _roomOption: RoomOption? = null,

    /* Relation Parent Mapping */
    @OneToMany(mappedBy = "_room", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    private var _reservationRooms: MutableList<ReservationRoom> = mutableListOf(),

    /* Relation Child Mapping */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private var _category: Category? = null,
) {
    val id: Long?
        get() = _id
    val floor: Int
        get() = _floor
    val roomNumber: Int
        get() = _roomNumber
    val size: Double
        get() = _size
    val status: EVisibleStatus
        get() = _status
    val bedroomCnt: Int
        get() = _bedroomCnt
    val bathroomCnt: Int
        get() = _bathroomCnt
    val livingRoomCnt: Int
        get() = _livingRoomCnt
    val kitchenCnt: Int
        get() = _kitchenCnt
    val terraceCnt: Int
        get() = _terraceCnt
    val bedType: EBedType
        get() = _bedType
    val bedCnt: Int
        get() = _bedCnt
    val roomOption: RoomOption?
        get() = _roomOption
    val reservationRooms: List<ReservationRoom>
        get() = _reservationRooms
    val category: Category?
        get() = _category

    fun updateRoom(
        category: Category,
        roomDto: RoomDto,
    ) {
        _floor = roomDto.floor
        _roomNumber = roomDto.number
        _size = roomDto.size
        _status = roomDto.status
        _bedroomCnt = roomDto.bedroomCnt
        _bathroomCnt = roomDto.bathroomCnt
        _livingRoomCnt = roomDto.livingRoomCnt
        _kitchenCnt = roomDto.kitchenCnt
        _terraceCnt = roomDto.terraceCnt
        _bedType = roomDto.bedType
        _bedCnt = roomDto.bedCnt

        _category = category
    }

    fun updateStatus(statusDto: StatusDto) {
        _status = statusDto.status
    }
}