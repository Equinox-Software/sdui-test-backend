package pxnx


import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.serialization.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo
import pxnx.model.UserProfile
import pxnx.util.MongoDBRepositories

fun main() {
    embeddedServer(CIO, System.getenv("PORT").toInt()) {
        //   configureSecurity()
        //////    configureRouting()
        //    configureSerialization()


        val client = KMongo.createClient(System.getenv("MONGODB-URI")).coroutine
        val dbProfiles = client.getDatabase("rw-dev")

        install(MongoDBRepositories) {

            repositoryPath = "data"

            collection<UserProfile>(dbProfiles) {
                collectionPath = "profile"
                addEndpoints(HttpMethod.Post, HttpMethod.Put, HttpMethod.Delete) {


                    addEndpoint(HttpMethod.Put) {

                        suspend {
                            Repository.createProfile("afefeef")
                        }

                    }



                    isAuthenticated = false
                }
            }
        }


    }.start(wait = true)
}


