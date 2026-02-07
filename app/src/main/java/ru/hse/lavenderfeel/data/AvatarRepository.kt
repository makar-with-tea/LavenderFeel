package ru.hse.lavenderfeel.data

import ru.hse.lavenderfeel.domain.Avatar

class AvatarRepository {

    fun getAvatar(): Avatar? =
        InMemoryStorage.avatar

    fun saveAvatar(avatar: Avatar) {
        InMemoryStorage.saveAvatar(avatar)
    }
}