package com.example.cryptoapp.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoapp.R
import com.example.cryptoapp.data.network.ApiFactory
import com.example.cryptoapp.utils.convertTimestampToTime
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_coin_detail.*

class CoinDetailActivity : AppCompatActivity() {
    private lateinit var viewModel: CoinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_detail)
        //если не EXTRA_FROM_SYMBOL, то выходим их активити
        if (!intent.hasExtra(EXTRA_FROM_SYMBOL)) {
            finish()
            return
        }
        //получаем переменную из адаптера
        val fromSymbol = intent.getStringExtra(EXTRA_FROM_SYMBOL) ?: EMPTY_SYMBOL

        //настроим вьюмодель
        viewModel = ViewModelProvider(this).get(CoinViewModel::class.java)
        //получаем детальную информацию
        viewModel.getDetailInfo(fromSymbol).observe(this) {
            Log.d("MyLog", " it.tostring() $it")
            tvS.text = it.fromSymbol
            tvU.text = it.toSymbol
            tvPriceVolume.text = it.price
            tvMinV.text = it.lowDay
            tvMaxV.text = it.highDay
            tvLastSdelkaV.text = it.lastMarket
            tvUpdateV.text = convertTimestampToTime(it.lastUpdate)
            Picasso.get().load(ApiFactory.BASE_IMAGE_URL + it.imageUrl).into(ivLogo)
        }

    }

    companion object {
        private const val EXTRA_FROM_SYMBOL = "fSym"
        private const val EMPTY_SYMBOL = ""

        //другой способ вызова активити
        fun newInstance(context: Context, fromSymbol: String): Intent {
            val intent = Intent(context, CoinDetailActivity::class.java)
            intent.putExtra(EXTRA_FROM_SYMBOL, fromSymbol)
            return intent
        }
    }
}