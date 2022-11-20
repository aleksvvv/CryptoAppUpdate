package com.example.cryptoapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoapp.R
import com.example.cryptoapp.pojo.CoinInfo
import com.example.cryptoapp.pojo.CoinPriceInfo
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_coin_info.view.*

//пробросили private val context: Context, в качестве параметра конструктора
class CoinInfoAdapter(private val context: Context):RecyclerView.Adapter<CoinInfoAdapter.CoinInfoViewHolder>() {

    var coinInfoList:List<CoinPriceInfo> = listOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }
    //создаем пременную, которой можно присвоить значение из активити
    var onCoinClickListener: OnCoinClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_coin_info, parent, false)
        return CoinInfoViewHolder(view)
    }
    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val coin = coinInfoList.get(position)
        val symbolsTemplate = context.resources.getString(R.string.symbols_template)
        val lastUpdateTemplate = context.resources.getString(R.string.last_update_template)
        holder.tvSymbols.text = String.format(symbolsTemplate, coin.fromSymbol, coin.toSymbol)
     //   holder.tvSymbols.text = coin.fromSymbol + "/" + coin.toSymbol
        holder.tvPrice.text = coin.price
        holder.tvListUpdate.text = String.format(lastUpdateTemplate,coin.getFormattedTime())
     // holder.tvListUpdate.text = "Время последнего обновления:" + coin.getFormattedTime()
        Picasso.get().load(coin.getFullImageUrl()).into(holder.ivLogoCoin)
        //слушатель нажатий при клике на itemView
        holder.itemView.setOnClickListener{
            onCoinClickListener?.onCoinClick(coin)

        }

    }
    override fun getItemCount() = coinInfoList.size

    inner class CoinInfoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
//импорт с помощью синтетика
        val ivLogoCoin = itemView.ivLogoCoin
        val tvSymbols = itemView.tvSymbols
        val tvPrice = itemView.tvPrice
        val tvListUpdate=  itemView.tvListUpdate

    }
    //создаем интерфейс для слушателя клика
    interface OnCoinClickListener {
        fun onCoinClick(coinPriceInfo: CoinPriceInfo)
    }

}