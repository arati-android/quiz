package com.example.practcaltest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.practcaltest.comman.NavigationActivity
import com.example.practcaltest.databinding.ListquestinansBinding
import com.example.practcaltest.response.QuestionAnsData


class AdapterQues(var data: (String, String) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var differ = AsyncListDiffer(this, DiffCallback())
    var answered: Boolean = false
    var countRight: Int = 0
    var countWrong: Int = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListquestinansBinding.inflate(inflater, parent, false)
        return ViewDataHolder(binding)
    }
   inner class ViewDataHolder(var binding: ListquestinansBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: QuestionAnsData?, position: Int) {
            binding.getData = item
            answered=false
            val nav=itemView.context as NavigationActivity


            binding.RGroup.setOnCheckedChangeListener { radioGroup, i ->

                val id: Int = binding.RGroup.checkedRadioButtonId
                val rb: RadioButton = binding.RGroup.findViewById(id)
                val radioText = rb.text.toString()
                answered

                if (!answered){
                    binding.right.isEnabled=false
                    binding.wrong.isEnabled=false
                }else{
                    binding.right.isEnabled=true
                    binding.wrong.isEnabled=true
                }

                if (item != null) {
                    if (item.correct_answer==radioText){
                        if (item != null) {
                            countRight ++
                            data.invoke(countRight.toString(),countWrong.toString())
                        }
                    }else{
                        countWrong++
                        data.invoke(countRight.toString(),countWrong.toString())
                    }
                }

                if (position==differ.currentList.size-1){
                    nav.openResultActivity()
                }
            }


        }

    }
    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewDataHolder) {
            holder.bindView(differ.currentList[position],position)
        }
    }

    open class DiffCallback :
        DiffUtil.ItemCallback<QuestionAnsData>() {
        override fun areItemsTheSame(
            oldItem: QuestionAnsData,
            newItem: QuestionAnsData
        ): Boolean {
            return oldItem.question == newItem.question
        }

        override fun areContentsTheSame(
            oldItem: QuestionAnsData,
            newItem: QuestionAnsData
        ): Boolean {
            return oldItem == newItem
        }


    }
}


