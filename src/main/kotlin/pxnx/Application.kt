package pxnx

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.serialization.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import kotlinx.serialization.json.Json
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo
import pxnx.model.UserProfile
import pxnx.util.MongoDBRepositories

fun main() {
    embeddedServer(CIO, System.getenv("PORT").toInt()) {
        //   configureSecurity()

        //    configureSerialization()

        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }


        val client = KMongo.createClient(System.getenv("MONGODB")).coroutine
        val dbProfiles = client.getDatabase("rw-dev")

    //    configureRouting()

        install(MongoDBRepositories) {

            repositoryPath = "data"

            collection<UserProfile>(dbProfiles) {
                collectionPath = "profile"
                addEndpoints( HttpMethod.Put) {

print("------------------ PUT -------------------\n\n.")
                        suspend {
                            Repository.createProfile("afefeef")
                        }

                    isAuthenticated = false
                    }




                }

        }


    }.start(wait = true)
}


