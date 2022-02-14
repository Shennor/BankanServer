package ru.kfd.bankan.bankanserver.payload.response

import ru.kfd.bankan.bankanserver.entity.UserSettingsEntity
import java.lang.reflect.Array.get

class SettingsResponse(
    val settings: Any?
)

val UserSettingsEntity.asResponse: SettingsResponse
    get() = SettingsResponse(
        settings
    )

