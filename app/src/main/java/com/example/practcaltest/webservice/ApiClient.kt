import android.util.Log
import com.example.practcaltest.webservice.ApiService
import com.intuit.sdp.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    private const val readTimeOut: Long = 50
    private const val writeTimeOut: Long = 60
    private const val connectionTimeOut: Long = 50
    private const val Base_URL = "https://opentdb.com/"

    fun getClient(): ApiService {
        val okHttpClient = OkHttpClient.Builder().run {
            // debuge
            if (BuildConfig.DEBUG) {
                val logging = HttpLoggingInterceptor()
                logging.level = HttpLoggingInterceptor.Level.BODY
                addInterceptor(logging)
//             Controller.instance.context?.let {
//                    addInterceptor(ChuckerInterceptor.Builder(it.baseContext).build())
//                }
            }

            addInterceptor(Interceptor { chain ->
               // val token: String? = AppPreferences.authToken
                val requestBuilder = chain.request().newBuilder()
//                token?.let {
//                    Log.e("token", "Bearer $it")
//                    requestBuilder.addHeader("Authorization", "Bearer $it")
//                }
               // requestBuilder.addHeader("Accept", "application/json")
                //requestBuilder.addHeader("localization", AppPreferences.language)
                chain.proceed(requestBuilder.build())
            })
            connectTimeout(connectionTimeOut, TimeUnit.SECONDS)
            readTimeout(readTimeOut, TimeUnit.SECONDS)
            writeTimeout(writeTimeOut, TimeUnit.SECONDS)
            build()

        }

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Base_URL)
            .client(okHttpClient)
            .build()
            .create(ApiService::class.java)
    }
}

