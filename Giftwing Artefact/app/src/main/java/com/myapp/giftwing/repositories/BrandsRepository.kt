package com.myapp.giftwing.repositories

import com.myapp.giftwing.data.local.RoomDao
import com.myapp.giftwing.models.Advertisement
import com.myapp.giftwing.models.Manufacturer
import com.myapp.giftwing.sealed.DataResponse
import com.myapp.giftwing.sealed.AllError
import com.myapp.giftwing.utils.getStructuredManufacturers
import javax.inject.Inject

class BrandsRepository @Inject constructor(
    private val dao: RoomDao,
) {
    suspend fun getBrandsAdvertisements(): DataResponse<List<Advertisement>> {
        /** First we should check the local storage */
        dao.getAdvertisements().let {
            return if (it.isNotEmpty()) {
                DataResponse.Success(data = it)
            } else {
                /** Now we should fetch from the remote server */
                DataResponse.Error(allError = AllError.Empty)
            }
        }
    }

    suspend fun getBrandsWithProducts(): DataResponse<List<Manufacturer>> {
        /** First we should check the local storage */
        dao.getManufacturersWithProducts().getStructuredManufacturers().let {
            return if (it.isNotEmpty()) {
                DataResponse.Success(data = it)
            } else {
                /** Now we should fetch from the remote server */
                DataResponse.Error(allError = AllError.Empty)
            }
        }
    }
}

