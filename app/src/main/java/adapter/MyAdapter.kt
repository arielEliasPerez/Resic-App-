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
import product.ProductDetailActivity

class MyAdapter(private val products : List<Product>, private var context: Context) : RecyclerView.Adapter<MyAdapter.MyViewHolder>()
{
    class MyViewHolder(private val row: View, private val context: Context) :
        RecyclerView.ViewHolder(row) {
        private val pImage = row.findViewById<ImageView>(R.id.item_p_logo)!!
        private val pName  = row.findViewById<TextView>(R.id.tv_p_title)
        private val pPrice = row.findViewById<TextView>(R.id.tv_p_price)
        private val pType  = row.findViewById<TextView>(R.id.tv_p_type)

        fun bind(product: Product) {
            pImage.setImageResource(product.logo)
            pName.text  = product.name
            pPrice.text = context.getString(R.string.cash , product.price.toString())
            pType.text  = product.type.name.lowercase()

            row.setOnClickListener {
                val intent = Intent(context, ProductDetailActivity::class.java).putExtra("idProduct", product.id)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.products_view, parent, false)
        return MyViewHolder(layout, context)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val product = products[position]
        holder.bind(product = product)
    }

    override fun getItemCount(): Int = products.size

}