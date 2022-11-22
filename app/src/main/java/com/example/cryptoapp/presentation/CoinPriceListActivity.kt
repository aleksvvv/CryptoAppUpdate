package com.example.cryptoapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.ActivityCoinPriceListBinding
import com.example.cryptoapp.domain.CoinInfoEntity
import com.example.cryptoapp.presentation.adapter.CoinInfoAdapter
import io.reactivex.disposables.CompositeDisposable


class CoinPriceListActivity : AppCompatActivity() {
    private val compositeDisposable = CompositeDisposable()
    private lateinit var viewModel: CoinViewModel
    private val binding by lazy {
        ActivityCoinPriceListBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //создадим адаптер
        val adapter = CoinInfoAdapter(this)
        //установим слушатель клика
        adapter.onCoinClickListener = object : CoinInfoAdapter.OnCoinClickListener {
            override fun onCoinClick(coinPriceInfo: CoinInfoEntity) {
             if (isOnePaneMode()){
                 launchDetailActivity(coinPriceInfo.fromSymbol)
             }else{
                 launchDetailFragment(coinPriceInfo.fromSymbol)
             }
//                val intent = CoinDetailActivity.newInstance(
//                    this@CoinPriceListActivity,
//                    coinPriceInfo.fromSymbol
//                )
//                startActivity(intent)
            }
        }
        //установим адаптер у рецайклервью
        binding.rvCoinPriceList.adapter = adapter
//отключаем анимацию
        binding.rvCoinPriceList.itemAnimator = null
//создали вью модель
        viewModel = ViewModelProvider(this).get(CoinViewModel::class.java)

        viewModel.coinInfoList.observe(this) {
//            adapter.coinInfoList = it
            adapter.submitList(it)
        }
    }
    private fun isOnePaneMode() = binding.fragmentContainer == null

    private fun launchDetailActivity(fromSymbol: String){
        val intent = CoinDetailActivity.newInstance(
            this@CoinPriceListActivity,
            fromSymbol
        )
        startActivity(intent)
    }
    private fun launchDetailFragment(fromSymbol:String){
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, CoinDetailFragment.newInstance(fromSymbol))
            .addToBackStack(null)
            .commit()
    }
    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}