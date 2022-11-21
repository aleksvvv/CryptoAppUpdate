package com.example.cryptoapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoapp.R
import com.example.cryptoapp.presentation.adapter.CoinInfoAdapter
import com.example.cryptoapp.data.network.ApiFactory
import com.example.cryptoapp.data.network.model.CoinInfoDto
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_coin_price_list.*


class CoinPriceListActivity : AppCompatActivity() {
    private val compositeDisposable = CompositeDisposable()
    private lateinit var viewModel: CoinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_price_list)
        //создадим адаптер
        val adapter = CoinInfoAdapter(this)
        //установим слушатель клика
        adapter.onCoinClickListener = object: CoinInfoAdapter.OnCoinClickListener {
            override fun onCoinClick(coinPriceInfo: CoinInfoDto) {
              //  val intent = Intent(this@CoinPriceListActivity,CoinDetailActivity::class.java)
               // intent.putExtra(CoinDetailActivity.EXTRA_FROM_SYMBOL,coinPriceInfo.fromSymbol)
                val intent = CoinDetailActivity.newInstance(
                    this@CoinPriceListActivity,
                    coinPriceInfo.fromSymbol
                )
                startActivity(intent)

                //Log.d("MyLog", "Valuta: ${coinPriceInfo.fromSymbol}")
            }

        }
        //установим адаптер у рецайклервью
        rvCoinPriceList.adapter = adapter
//создали вью модель
        viewModel = ViewModelProvider(this).get(CoinViewModel::class.java)


        viewModel.priceList.observe(this, Observer {
adapter.coinInfoList = it


//            Log.d("MyLog", "Success in Activity: $it")
        })

        //подписались на детальную информацию о биткоине
//        viewModel.getDetailInfo("BTC").observe(this, Observer {
//            Log.d("MyLog", "Success in Activity btc:  $it")
//        })

        val disposable = ApiFactory.apiService.getFullPriceList( fSyms = "BTC,ETH,EOS")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
            //    Log.d("MyLog", it.toString())
            },{
              //  Log.d("MyLog", "it.message")
            }
                            )
        compositeDisposable.add(disposable)

    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
   }
}