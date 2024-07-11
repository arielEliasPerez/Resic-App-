package adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.resicapp.R
import data.Product
import data.Purchase
import user.PurchaseDetailsActivity
import repositories.ProductRepository

class HistoryAdapter(private val purchases : List<Purchase>, private var context : Context): RecyclerView.Adapter<HistoryAdapter.MyViewHolder>()
{
    class MyViewHolder(private val row : View, private val context: Context) : RecyclerView.ViewHolder(row){
        private val itemDate = row.findViewById<TextView>(R.id.tv_h_date)
        private val itemTitle = row.findViewById<TextView>(R.id.tv_h_product_title)
        private val itemSpent = row.findViewById<TextView>(R.id.tv_h_spent)
        private val itemType = row.findViewById<TextView>(R.id.tv_h_product_type)

        fun bind(purchase: Purchase){
            val product = findProduct(purchase)

            itemDate.text  = context.getString(R.string.history_date , purchase.createdDate)
            itemTitle.text = context.getString(R.string.history_title , product.name)
            itemSpent.text = context.getString(R.string.history_spent , purchase.amount.toString())
            itemType.text  = context.getString(R.string.history_productType , product.type.toString().lowercase())

            row.setOnClickListener{
                val intent = Intent(context , PurchaseDetailsActivity:: class.java )
                intent.putExtra("idPurchase" , purchase.id)
                context.startActivity(intent)
            }
        }

        private fun findProduct(purchase: Purchase): Product {
            return ProductRepository.getById(purchase.productId)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.history_details , parent , false)
        return MyViewHolder(layout , context)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val purchase = purchases[position]
        holder.bind(purchase = purchase)
    }

    override fun getItemCount(): Int = purchases.size

}