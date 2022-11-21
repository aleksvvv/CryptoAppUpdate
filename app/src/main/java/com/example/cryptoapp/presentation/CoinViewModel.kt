package com.example.cryptoapp.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.cryptoapp.data.network.ApiFactory
import com.example.cryptoapp.data.database.AppDatabase
import com.example.cryptoapp.data.network.model.CoinInfoDto
import com.example.cryptoapp.data.model.CoinPriceInfoRawData
import com.google.gson.Gson
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CoinViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getInstance(application)
    val priceList = db.coinPriceInfoDao().getPriceList()

    val compositeDisposable = CompositeDisposable()

            //выполняется при создании объекта этого класса
    init {
        loadData()
    }

    fun getDetailInfo(fSym: String): LiveData<CoinInfoDto>{
        return db.coinPriceInfoDao().getPriceInfoAboutCoin(fSym)
    }

    //получение данных из интернета
    private fun loadData() {
        val disposable = ApiFactory.apiService.getTopCoinsInfo()
            .map { it.names?.map { it.coinName?.name }?.joinToString(",") }
            .flatMap { ApiFactory.apiService.getFullPriceList(fSyms = it) }
            .map { getPriceListFromRawData(it) }
                //задает тайминг
            .delaySubscription(30, TimeUnit.SECONDS)
                //оператор rxjava повторяет
            .repeat()
                //продолжается выполнение программы несмотря на сбой
            .retry()
            .subscribeOn(Schedulers.io())
          //  .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
//it.data?.map { it.coinInfo }
               Log.d("MyLog", "Success: $it")
db.coinPriceInfoDao().insertPriceList(it)
                  //  Log.d("MyLog", "it $it")

                },
                {
                    Log.d("MyLog", "Failure: ${it.message} ")
                }
            )
        compositeDisposable.add(disposable)
    }

    fun getPriceListFromRawData(
        coinPriceInfoRawData: CoinPriceInfoRawData
    ):
            List<CoinInfoDto> {
        val result = ArrayList<CoinInfoDto>()
//        val jsonObject = coinPriceInfoRawData.coinPriceInfoJsonObject
//        if (jsonObject == null) return result
        //или через оператор элвис
        val jsonObject = coinPriceInfoRawData.coinPriceInfoJsonObject ?:return result

        val coinKeySet = jsonObject.keySet()
        for (coinKey in coinKeySet) {
            val currencyJson = jsonObject.getAsJsonObject(coinKey)
            val currencyKeySet = currencyJson.keySet()
            for (currencyKey in currencyKeySet) {
                val priceInfo = Gson().fromJson(
                    currencyJson.getAsJsonObject(currencyKey),
                    CoinInfoDto::class.java
                )
                result.add(priceInfo)
            }

        }
return result

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}