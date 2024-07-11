package adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.resicapp.R
import data.Category
import product.FindProductsActivity

class CategoryAdapter(private val categories : List<Category>, private var context: Context) : RecyclerView.Adapter<CategoryAdapter.MyViewHolder>()
{
    class MyViewHolder(private val row: View, private val context: Context) :
        RecyclerView.ViewHolder(row) {
        private  var itemCategory = row.findViewById<TextView>(R.id.itemCategory)!!

        fun bind(category: Category) {
            itemCategory.text = category.name

            row.setOnClickListener{
                val intent = Intent(context, FindProductsActivity::class.java).putExtra("NameCategory", category.name)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):MyViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.category_view , parent , false )
        return MyViewHolder(layout , context)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val category = categories[position]
        holder.bind(category = category)
    }

    override fun getItemCount(): Int = categories.size


}