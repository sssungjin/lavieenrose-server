package org.dongguk.lavieenrosehotel.service

import jakarta.transaction.Transactional
import org.dongguk.lavieenrosehotel.domain.PaymentHistory
import org.dongguk.lavieenrosehotel.domain.Reservation
import org.dongguk.lavieenrosehotel.domain.User
import org.dongguk.lavieenrosehotel.dto.type.EReservationStatus
import org.dongguk.lavieenrosehotel.event.ReservationEvent
import org.dongguk.lavieenrosehotel.event.PaymentVerifyEvent
import org.dongguk.lavieenrosehotel.event.ReservationCancelEvent
import org.dongguk.lavieenrosehotel.exception.CommonException
import org.dongguk.lavieenrosehotel.exception.ErrorCode
import org.dongguk.lavieenrosehotel.repository.PaymentHistoryRepository
import org.dongguk.lavieenrosehotel.repository.ReservationRepository
import org.dongguk.lavieenrosehotel.repository.UserRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service


@Service
class PaymentService(
    private val userRepository: UserRepository,
    private val reservationRepository: ReservationRepository,
    private val paymentHistoryRepository: PaymentHistoryRepository,
    private val publisher: ApplicationEventPublisher,
) {
    fun verifyPayment(event: ReservationEvent) {
        // Import Verify
        val verify: Boolean = true;

        if (verify) {
            publisher.publishEvent(
                PaymentVerifyEvent(
                    reservationId =  event.reservationId,
                    status = EReservationStatus.DEPOSIT_CONFIRMATION,)
            )

            val user: User = userRepository.findById(event.userId).orElseThrow {
                CommonException(
                    ErrorCode.NOT_FOUND_ENTITY_AMONG_PROCESSING_EVENT
                )
            }

            val reservation: Reservation = reservationRepository.findById(event.reservationId).orElseThrow {
                CommonException(
                    ErrorCode.NOT_FOUND_ENTITY_AMONG_PROCESSING_EVENT
                )
            }

            paymentHistoryRepository.save(PaymentHistory.fromEvent(
                event = event,
                reservation = reservation,
                user = user,
            ))
        }

    }

    @Transactional
    fun cancelPayment(event: ReservationCancelEvent) {
        // Import Verify
        val isCancel: Boolean = true;

        if (isCancel) {
            publisher.publishEvent(
                PaymentVerifyEvent(
                    reservationId =  event.reservationId,
                    status = EReservationStatus.CANCEL,
                )
            )

            val reservation: Reservation = reservationRepository.findById(event.reservationId).orElseThrow {
                CommonException(
                    ErrorCode.NOT_FOUND_ENTITY_AMONG_PROCESSING_EVENT
                )
            }

            reservation.paymentHistories!!.refund(reservation.paymentPrice)
        }
    }
}