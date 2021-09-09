package pxnx

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.request.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import pxnx.security.kk
import org.slf4j.event.Level
import pxnx.security.setUpJWT

fun main() {
    embeddedServer(CIO, System.getenv("PORT").toInt()) {
        //   configureSecurity()

        //    configureSerialization()

   /*     install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                //   isLenient = true
                ignoreUnknownKeys = true
            })
        }

    */

        install(CallLogging) {
            level = Level.INFO
            filter { call -> call.request.path().startsWith("/") }
        }

/*
        val client = KMongo.createClient(System.getenv("MONGODB")).coroutine

        val dbProfiles = client.getDatabase("rw-dev")

        //    configureRouting()

        install(MongoDBRepositories) {

            repositoryPath = "data"

            collection<UserProfile>(dbProfiles) {
                collectionPath = "profile"
                addEndpoints(HttpMethod.Put, HttpMethod.Post, HttpMethod.Get) {

                    print("------------------ PUT -------------------\n\n.")
                    /*     suspend {
                             Repository.createProfile("afefeef")
                         } */

                    isAuthenticated = false
                }


            }

        }

 */

        kk()
       //   setUpJWT()


    }.start(wait = true)
}





