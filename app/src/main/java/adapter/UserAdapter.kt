package adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.resicapp.R
import data.Product
import data.Purchase
import user.ProductsUserDetailsActivity
import repositories.ProductRepository

class UserAdapter(private val purchases : List<Purchase>, private var context : Context): RecyclerView.Adapter<UserAdapter.MyViewHolder>()
{
    class MyViewHolder(private val row : View, private val context: Context) : RecyclerView.ViewHolder(row){
        private val itemImage = row.findViewById<ImageView>(R.id.item_p_logo)
        private val itemTitle = row.findViewById<TextView>(R.id.tv_p_title)
        private val itemAuthor = row.findViewById<TextView>(R.id.tv_author)
        private val itemType = row.findViewById<TextView>(R.id.tv_p_type)

        fun bind(purchase: Purchase){
            val product = findProduct(purchase)

            itemImage.setImageResource(product.logo)
            itemTitle.text = product.name
            itemAuthor.text = product.author
            itemType.text = product.type.toString().lowercase()

            row.setOnClickListener{
                val intent = Intent(context , ProductsUserDetailsActivity :: class.java).putExtra("idPurchase" , purchase.id)
                context.startActivity(intent)
            }
        }

        private fun findProduct(purchase: Purchase): Product {
            return ProductRepository.getById(purchase.productId)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.user_products , parent , false)
        return MyViewHolder(layout , context)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val purchase = purchases[position]
        holder.bind(purchase = purchase)
    }

    override fun getItemCount(): Int = purchases.size

}
