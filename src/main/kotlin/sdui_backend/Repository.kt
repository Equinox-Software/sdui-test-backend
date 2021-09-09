package sdui_backend

import org.litote.kmongo.coroutine.aggregate
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.eq
import org.litote.kmongo.match
import org.litote.kmongo.reactivestreams.KMongo
import org.litote.kmongo.sample
import sdui_backend.model.UserProfile


object Repository {

    private val client = KMongo.createClient().coroutine
    private val dbProfiles = client.getDatabase("rw-dev")

    suspend fun createProfile(id: String) =
        dbProfiles.getCollection<UserProfile>("userProfiles").insertOne(UserProfile(id))


    suspend fun getUserProfile(id: String) =
        dbProfiles.getCollection<UserProfile>("userProfiles").aggregate<UserProfile>(
            match(UserProfile::id eq id),
            sample(1)
        ).first()


}