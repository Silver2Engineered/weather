import android.app.Application
import com.example.weather.network.CityRoomDatabase

class Application : Application(){
    val database: CityRoomDatabase by lazy { CityRoomDatabase.getDatabase(this) }
}