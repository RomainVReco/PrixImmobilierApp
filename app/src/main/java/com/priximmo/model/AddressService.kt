import com.priximmo.dataclass.AddressResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AddressService {
    @GET("search")
    fun searchAddress(@Query("q") query: String): Call<AddressResponse>
}