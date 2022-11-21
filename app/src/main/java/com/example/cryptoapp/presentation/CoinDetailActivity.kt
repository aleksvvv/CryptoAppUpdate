package com.example.cryptoapp.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoapp.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_coin_detail.*

class CoinDetailActivity : AppCompatActivity() {
    private lateinit var viewModel: CoinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_detail)
        //если не EXTRA_FROM_SYMBOL, то выходим их активити
          if (!intent.hasExtra(EXTRA_FROM_SYMBOL)){
            finish()
              return
        }
        //получаем переменную из адаптера
        val fromSymbol = intent.getStringExtra(EXTRA_FROM_SYMBOL)!!

        //настроим вьюмодель
        viewModel = ViewModelProvider(this).get(CoinViewModel::class.java)
        //получаем детальную информацию
        viewModel.getDetailInfo(fromSymbol).observe(this, Observer {
            Log.d("MyLog", " it.tostring() $it")
            tvS.text = it.fromSymbol
            tvU.text = it.toSymbol
            tvPriceVolume.text = it.price
           tvMinV.text = it.lowday
            tvMaxV.text = it.highday
            tvLastSdelkaV.text =it.lastmarket
            tvUpdateV.text = it.getFormattedTime()
            Picasso.get().load(it.getFullImageUrl()).into(ivLogo)

        })

    }
    companion object{
        private const val EXTRA_FROM_SYMBOL = "fSym"
        //другой способ вызова активити
        fun newInstance(context: Context, fromSymbol: String): Intent{
            val intent = Intent(context, CoinDetailActivity::class.java)
            intent.putExtra(EXTRA_FROM_SYMBOL, fromSymbol)
            return intent
        }
    }
}