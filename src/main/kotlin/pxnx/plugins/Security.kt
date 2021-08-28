package pxnx.plugins

import com.auth0.jwt.JWT
import io.ktor.auth.*
import io.ktor.util.*
import io.ktor.client.*
import io.ktor.locations.*
import io.ktor.http.*
import io.ktor.application.*
import io.ktor.auth.jwt.*
import io.ktor.client.engine.cio.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import pxnx.JwtService
import pxnx.model.User

//import sun.security.util.KeyUtil.validate

fun Application.configureSecurity() {

    install(Authentication) {
        oauth("auth-oauth-google") {
            urlProvider = { "http://localhost:8080/callback" }
            providerLookup = {
                OAuthServerSettings.OAuth2ServerSettings(
                    name = "google",
                    authorizeUrl = "https://accounts.google.com/o/oauth2/auth",
                    accessTokenUrl = "https://accounts.google.com/o/oauth2/token",
                    requestMethod = HttpMethod.Post,
                    clientId = System.getenv("GOOGLE_CLIENT_ID"),
                    clientSecret = System.getenv("GOOGLE_CLIENT_SECRET"),
                    defaultScopes = listOf("https://www.googleapis.com/auth/userinfo.profile")
                )
            }
            client = HttpClient(CIO)
        }



       jwt {





/*           val secret = environment.config.property("jwt.secret").getString()
           val issuer = environment.config.property("jwt.issuer").getString()
           val audience = environment.config.property("jwt.audience").getString()
           val myRealm = environment.config.property("jwt.realm").getString()

 */

          verifier(JwtService.verifier)
           realm = "Access to 'hello'"
           /*       validate {
                    val userId = it.payload.getClaim(CLAIM_USERID).asInt()
                     val user = mongoDataHandler.finduser(userId) // 4
                     PrincipalUser(user?.userId!!.toString())



                 }

          *//////




        }


    }

    routing {
        authenticate("auth-oauth-google") {
            get("login") {
                call.respondRedirect("/callback")
            }

            get("/callback") {
                val principal: OAuthAccessTokenResponse.OAuth2? = call.authentication.principal()
                //   call.sessions.set(UserSession(principal?.accessToken.toString()))
                call.respondRedirect("/hello")
            }
        }

        post("/loginJWT") {
            val user = call.receive<User>()
            // Check username and password
            // ...


       /*


            val token = JWT.create()
                .withAudience(audience)
                .withIssuer(issuer)
                .withClaim("username", user.username)
                .withExpiresAt(Date(System.currentTimeMillis() + 60000))
                .sign(Algorithm.HMAC256(secret))
            call.respond(hashMapOf("token" to token))

        */
        }
    }
}
