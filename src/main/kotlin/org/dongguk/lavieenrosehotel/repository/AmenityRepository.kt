package org.dongguk.lavieenrosehotel.repository

import org.dongguk.lavieenrosehotel.domain.Amenity
import org.dongguk.lavieenrosehotel.dto.type.EAmenityType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AmenityRepository: JpaRepository<Amenity, Long> {

    @Query("SELECT a FROM Amenity a WHERE a._type = :type")
    fun findByAmenityType(type: EAmenityType?): Optional<Amenity>
}