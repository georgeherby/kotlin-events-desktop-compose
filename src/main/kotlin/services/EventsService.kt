package services

import models.Events
import okhttp3.OkHttpClient
import org.simpleframework.xml.convert.AnnotationStrategy
import org.simpleframework.xml.core.Persister
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import retrofit2.http.GET
import services.utils.NetworkInterceptor

interface EventsService {

    @GET("JetBrains/kotlin-web-site/master/data/events.xml")
    fun get(): Call<Events>


    companion object {
        operator fun invoke(): EventsService {

            val client = OkHttpClient.Builder().addInterceptor(NetworkInterceptor).build()

            return Retrofit
                .Builder()
                .baseUrl("https://raw.githubusercontent.com/")
                .client(client)
                .addConverterFactory(
                    SimpleXmlConverterFactory.createNonStrict(
                        Persister(
                            AnnotationStrategy()
                        )
                    )
                )
                .build()
                .create(EventsService::class.java)
        }
    }

}
