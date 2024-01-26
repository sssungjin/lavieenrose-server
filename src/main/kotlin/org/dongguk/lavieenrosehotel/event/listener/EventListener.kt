package org.dongguk.lavieenrosehotel.event.listener

import org.dongguk.lavieenrosehotel.event.PaymentVerifyEvent
import org.dongguk.lavieenrosehotel.event.ReservationCancelEvent
import org.dongguk.lavieenrosehotel.event.ReservationEvent
import org.dongguk.lavieenrosehotel.service.PaymentService
import org.dongguk.lavieenrosehotel.service.ReservationService
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class EventListener(
    private val paymentService: PaymentService,
    private val reservationService: ReservationService, ) {

    @Async
    @EventListener
    fun onReservationEvent(event: ReservationEvent) {
        println("예약 이벤트 발생")
        paymentService.verifyPayment(event)
    }

    @Async
    @EventListener
    fun onPaymentVerifyEvent(event: PaymentVerifyEvent) {
        println("결제 검증 이벤트 발생")
        reservationService.updateStatus(event)
    }

    @Async
    @EventListener
    fun onReservationCancelEvent(event: ReservationCancelEvent) {
        println("예약 취소 이벤트 발생")
        paymentService.cancelPayment(event)
    }
}