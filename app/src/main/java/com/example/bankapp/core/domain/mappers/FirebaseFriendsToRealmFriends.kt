package com.example.bankapp.core.domain.mappers

import com.example.bankapp.core.data.local.realm.model.FriendRealm
import com.example.bankapp.core.data.remote.firebase.model.FriendFireStore
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject

fun mapFriendsFireStoreToFriendsRealm(friends: List<FriendFireStore>): RealmList<FriendRealm> {
    val realmFriends = realmListOf<FriendRealm>()
    friends.forEach { friend ->
        realmFriends.add(mapFriendFireStoreToFriendRealm(friend))
    }
    return realmFriends
}

fun mapFriendFireStoreToFriendRealm(friend: FriendFireStore): FriendRealm {
    return FriendRealm().apply {
            name = friend.name
            email = friend.email
            phone = friend.phone
            profilePicUrl = friend.profilePicUrl
    }
}
