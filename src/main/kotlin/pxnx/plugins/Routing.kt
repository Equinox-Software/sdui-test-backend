package pxnx.plugins

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import kotlinx.serialization.json.Json
import pxnx.Repository


@OptIn(KtorExperimentalLocationsAPI::class)
fun Application.configureRouting() {


    install(Locations) {
    }

    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }

    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        get<MyLocation> {
            call.respondText("Location: name=${it.name}, arg1=${it.arg1}, arg2=${it.arg2}")
        }
        // Register nested routes
        get<Type.Edit> {
            call.respondText("Inside $it")
        }
        get<Type.List> {
            call.respondText("Inside $it")
        }




        get("/profile/{id}/createGET") {


            call.parameters["id"]?.let { id ->
                call.getData(Repository.createProfile(id), "Couldn't createeee")

            }

        }

        get<UserProfileLocation> {
            call.getData(Repository.getUserProfile(it.id), "UserProfile with ID ${it.id} does not exist.")
        }


        ////////
        put("/profile/{id}/create") {


            call.parameters["id"]?.let { id ->
                call.setData(Repository.createProfile(id), "Couldn't createeee")

            }
        }
    }
}


suspend inline fun <reified T> ApplicationCall.getData(value: T, message: String) {
    return try {
        value?.let { result -> this.respond(result) } ?: this.respond(HttpStatusCode.NotFound, message)
    } catch (e: Exception) {
        this.respond(HttpStatusCode.InternalServerError, e.message!!)
    }
}

suspend inline fun <reified T> ApplicationCall.setData(value: T, message: String) {
    return try {
        value?.let { result -> this.respond(result) } ?: this.respond(HttpStatusCode.BadRequest, message)
    } catch (e: Exception) {
        this.respond(HttpStatusCode.InternalServerError, e.message!!)
    }
}


/*

private suspend fun addOne(id: String){
    col.insertOne(User(id, 27))

    println("------------\n\n DB: $db")




  val ss=  col.findOne(User::name eq "NNNNNNN")

    println("------------\n\n ss: $ss")
}


private fun getAll(): CoroutineFindPublisher<User> {





 //   val ss=  col.findOne(User::name eq "NNNNNNN")

    val ss = col.find()

    println("------------\n\n ss: $ss")

    return ss
}


 */











