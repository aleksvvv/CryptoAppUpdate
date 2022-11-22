package com.example.cryptoapp.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.ItemCoinInfoBinding
import com.example.cryptoapp.domain.CoinInfoEntity
import com.squareup.picasso.Picasso

//пробросили private val context: Context, в качестве параметра конструктора
class CoinInfoAdapter(
    private val context: Context
    ) :
    ListAdapter<CoinInfoEntity,CoinInfoViewHolder>(CoinInfoDiffCallback()) {

//    var coinInfoList: List<CoinInfoEntity> = listOf()
//        set(value) {
//            field = value
//            notifyDataSetChanged()
//        }

    //создаем пременную, которой можно присвоить значение из активити
    var onCoinClickListener: OnCoinClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        val binding = ItemCoinInfoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )

        return CoinInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val coin = getItem(position)
        val symbolsTemplate = context.resources.getString(R.string.symbols_template)
        val lastUpdateTemplate = context.resources.getString(R.string.last_update_template)
        with(holder.binding) {
            tvSymbols.text = String.format(symbolsTemplate, coin.fromSymbol, coin.toSymbol)
            //   holder.tvSymbols.text = coin.fromSymbol + "/" + coin.toSymbol
            tvPrice.text = coin.price
            tvListUpdate.text = String.format(lastUpdateTemplate, coin.lastUpdate)
            // holder.tvListUpdate.text = "Время последнего обновления:" + coin.getFormattedTime()
            Picasso.get().load(coin.imageUrl).into(ivLogoCoin)
            //слушатель нажатий при клике на itemView
            root.setOnClickListener {
                onCoinClickListener?.onCoinClick(coin)
            }
        }
    }

//    override fun getItemCount() = coinInfoList.size

    //создаем интерфейс для слушателя клика
    interface OnCoinClickListener {
        fun onCoinClick(coinPriceInfo: CoinInfoEntity)
    }

}