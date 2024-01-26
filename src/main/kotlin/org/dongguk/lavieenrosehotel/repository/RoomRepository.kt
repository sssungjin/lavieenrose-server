package org.dongguk.lavieenrosehotel.repository


import org.dongguk.lavieenrosehotel.domain.Room
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.util.Optional

@Repository
interface RoomRepository: JpaRepository<Room, Long> {

    @EntityGraph(attributePaths = ["_category", "_roomOption"])
    fun findBy_id(id: Long): Optional<Room>

    @Query("""
SELECT r FROM Room r 
WHERE r._category._id = :categoryId
AND r._category._maxCapacity >= :totalCnt
AND NOT EXISTS (
    SELECT 1 FROM ReservationRoom rr 
    WHERE rr._room = r 
    AND rr._startDate <= :endDate 
    AND rr._endDate > :startDate
)
ORDER BY r._roomNumber ASC
""")
    fun findAvailableRoomsByCategoryIdAndDateAndCnt(
        categoryId: Long?,
        startDate: LocalDate,
        endDate: LocalDate,
        totalCnt: Int
    ): Optional<List<Room>>

    @Query("""
    SELECT r FROM Room r WHERE r._category._maxCapacity >= :cnt
    AND NOT EXISTS (
        SELECT 1 FROM ReservationRoom rr WHERE rr._room = r 
        AND rr._startDate <= :endDate AND rr._endDate > :startDate
    )
""")
    fun findAvailableRoomsByDateAndCnt(
        startDate: LocalDate,
        endDate: LocalDate,
        cnt: Int
    ): Optional<List<Room>>


    @Query("""
SELECT r FROM Room r 
WHERE r._category._id = :categoryId
AND r._roomNumber = :roomNumber
AND r._category._maxCapacity >= :totalCnt
AND NOT EXISTS (
    SELECT 1 FROM ReservationRoom rr 
    WHERE rr._room = r 
    AND rr._startDate <= :endDate 
    AND rr._endDate > :startDate
)
""")
    fun findAvailableRoomByCategoryAndRoomNumberAndDateAndCnt(
        categoryId: Long,
        roomNumber: Int,
        startDate: LocalDate,
        endDate: LocalDate,
        totalCnt: Int
    ): Optional<List<Room>>

    @Query("SELECT r._id AS id, r._floor AS floor, r._roomNumber AS number, r._status AS status " +
            "FROM Room r")
    fun findListForm(pageable: Pageable) : Page<ListForm>

    @Query("SELECT r._id AS id, r._floor AS floor, r._roomNumber AS number, r._status AS status " +
            "FROM Room r WHERE r._floor = :floor")
    fun findListFormByFloor(floor: Int, pageable: Pageable) : Page<ListForm>

    interface ListForm {
        val id: Long
        val floor: Int
        val number: Int
        val status: String
    }
}