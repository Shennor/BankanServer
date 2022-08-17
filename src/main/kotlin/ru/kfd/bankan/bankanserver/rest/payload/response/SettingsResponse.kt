package ru.kfd.bankan.bankanserver.rest.payload.response

import ru.kfd.bankan.bankanserver.database.entity.UserSettingsEntity

class SettingsResponse(
    val settings: Any?
)

val UserSettingsEntity.asResponse: SettingsResponse
    get() = SettingsResponse(
        settings
    )

