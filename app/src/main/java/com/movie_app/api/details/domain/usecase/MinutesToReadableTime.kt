package com.movie_app.api.details.domain.usecase


class MinutesToReadableTime(
    private val minutes: Int
) {

    operator fun invoke(): String {
        return if (minutes < 60) {
            "$minutes min"
        } else {
            val hours = minutes / 60
            val remainingMinutes = minutes % 60

            String.format("%02d hr %02d min", hours, remainingMinutes)
        }
    }

}




















