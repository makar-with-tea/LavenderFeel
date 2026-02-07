package ru.hse.lavenderfeel.data

import ru.hse.lavenderfeel.domain.Avatar

class AvatarRepository {

    fun getAvatar(): Avatar? =
        SharedPreferencesStorage.getAvatar()

    fun saveAvatar(avatar: Avatar) {
        SharedPreferencesStorage.saveAvatar(avatar)
    }

    fun setName(name: String){
        SharedPreferencesStorage.updateAvatarName(name)
    }
}