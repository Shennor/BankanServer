package ru.kfd.bankan.bankanserver.payload.response

import ru.kfd.bankan.bankanserver.entity.UserSettingsEntity

class SettingsResponse(
    val settings: Any?
)

val UserSettingsEntity.asResponse: SettingsResponse
    get() = SettingsResponse(
        settings
    )

