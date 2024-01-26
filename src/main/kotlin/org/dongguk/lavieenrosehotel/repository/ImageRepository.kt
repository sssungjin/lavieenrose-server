package org.dongguk.lavieenrosehotel.repository

import org.dongguk.lavieenrosehotel.domain.Image
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ImageRepository: JpaRepository<Image, Long> {
}