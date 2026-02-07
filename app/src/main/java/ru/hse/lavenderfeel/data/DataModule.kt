package ru.hse.lavenderfeel.data

object DataModule {

    private val avatarRepository = AvatarRepository()
    private val dailyEntryRepository = DailyEntryRepository()

    val avatarService = AvatarService(avatarRepository)
    val dailyEntryService = DailyEntryService(dailyEntryRepository)
    val avatarRenderService = AvatarRenderService(avatarService, dailyEntryService)
}
