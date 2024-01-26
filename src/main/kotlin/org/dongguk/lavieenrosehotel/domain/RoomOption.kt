package org.dongguk.lavieenrosehotel.domain

import jakarta.persistence.*
import lombok.AccessLevel
import lombok.NoArgsConstructor

@Entity
@Table(name = "room_options")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class RoomOption(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private var _id: Long? = null,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private var _room: Room,

    @Column(name = "allow_smoking")
    private var _allowSmoking: Boolean,

    @Column(name = "allow_cooking")
    private var _allowCooking: Boolean,

    @Column(name = "exist_kitchenware")
    private var _existKitchenware: Boolean,

    @Column(name = "exist_microwave")
    private var _existMicrowave: Boolean,

    @Column(name = "exist_refrigerator")
    private var _existRefrigerator: Boolean,

    @Column(name = "exist_electric_kettle")
    private var _existElectricKettle: Boolean,

    @Column(name = "exist_tv")
    private var _existTv: Boolean,

    @Column(name = "exist_projector")
    private var _existProjector: Boolean,

    @Column(name = "exist_water_purifier")
    private var _existWaterPurifier: Boolean,

    @Column(name = "exist_coffee_machine")
    private var _existCoffeeMachine: Boolean,

    @Column(name = "exist_air_conditioner")
    private var _existAirConditioner: Boolean,

    @Column(name = "exist_hair_dryer")
    private var _existHairDryer: Boolean,

    @Column(name = "exist_swimming_pool")
    private var _existSwimmingPool: Boolean,

    @Column(name = "exist_bathtub")
    private var _existBathtub: Boolean,

    @Column(name = "exist_bidet")
    private var _existBidet: Boolean,

    @Column(name = "exist_whirlpool_spa")
    private var _existWhirlpoolSpa: Boolean,
) {
    val room: Room
        get() = _room
    val allowSmoking: Boolean
        get() = _allowSmoking
    val allowCooking: Boolean
        get() = _allowCooking
    val existKitchenware: Boolean
        get() = _existKitchenware
    val existMicrowave: Boolean
        get() = _existMicrowave
    val existRefrigerator: Boolean
        get() = _existRefrigerator
    val existElectricKettle: Boolean
        get() = _existElectricKettle
    val existTv: Boolean
        get() = _existTv
    val existProjector: Boolean
        get() = _existProjector
    val existWaterPurifier: Boolean
        get() = _existWaterPurifier
    val existCoffeeMachine: Boolean
        get() = _existCoffeeMachine
    val existAirConditioner: Boolean
        get() = _existAirConditioner
    val existHairDryer: Boolean
        get() = _existHairDryer
    val existSwimmingPool: Boolean
        get() = _existSwimmingPool
    val existBathtub: Boolean
        get() = _existBathtub
    val existBidet: Boolean
        get() = _existBidet
    val existWhirlpoolSpa: Boolean
        get() = _existWhirlpoolSpa

    fun update(options: List<Boolean>) {
        _allowSmoking = options[0]
        _allowCooking = options[1]
        _existKitchenware = options[2]
        _existMicrowave = options[3]
        _existRefrigerator = options[4]
        _existElectricKettle = options[5]
        _existTv = options[6]
        _existProjector = options[7]
        _existWaterPurifier = options[8]
        _existCoffeeMachine = options[9]
        _existAirConditioner = options[10]
        _existHairDryer = options[11]
        _existSwimmingPool = options[12]
        _existBathtub = options[13]
        _existBidet = options[14]
        _existWhirlpoolSpa = options[15]
    }
}