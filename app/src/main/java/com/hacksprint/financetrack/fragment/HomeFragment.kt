package com.hacksprint.financetrack.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.hacksprint.financetrack.presentation.ExpenseListAdapter
import com.hacksprint.financetrack.presentation.ExpenseViewModel
import com.hacksprint.financetrack.R
import com.hacksprint.financetrack.data.FinanceTrackDataBase
import com.hacksprint.financetrack.presentation.AdapterRvHome
import java.text.NumberFormat
import java.util.Locale

class HomeFragment : Fragment() {
    private lateinit var viewModel: ExpenseViewModel
    private val expenseAdapter by lazy { ExpenseListAdapter() }
    private lateinit var totalExpensesTextView: TextView

    // Chama novamente o banco de dados
    private val db by lazy {
        Room.databaseBuilder(
            requireContext(),
            FinanceTrackDataBase::class.java,
            "finance_track_db"
        ).build()
    }

    private val expenseDao by lazy { db.getExpenseDao() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        totalExpensesTextView = view.findViewById(R.id.valor_despesas)
        viewModel = ViewModelProvider(this).get(ExpenseViewModel::class.java)

        viewModel.totalExpenses.observe(viewLifecycleOwner) { total ->
            val format = NumberFormat.getCurrencyInstance(Locale.getDefault())
            totalExpensesTextView.text = format.format(total)
        }

        viewModel.loadTotalExpenses(expenseDao)

        val btnGrafic = view.findViewById<ImageView>(R.id.btn_grafic)
        btnGrafic.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame, ChartFragment())
                .addToBackStack(null)
                .commit()
        }

        val recyclerViewHome: RecyclerView = view.findViewById(R.id.rv_home)
        recyclerViewHome.layoutManager = GridLayoutManager(requireContext(), 2)
        val vazioImg: ImageView = view.findViewById(R.id.vazio_home)

        viewModel = ViewModelProvider(this).get(ExpenseViewModel::class.java)

        viewModel.expenses.observe(viewLifecycleOwner) { expenses ->
            if (expenses.isNullOrEmpty()) {
                vazioImg.visibility = View.VISIBLE
            } else {
                vazioImg.visibility = View.GONE

                val firstSixExpenses = expenses.take(6)
                val adapter = AdapterRvHome(firstSixExpenses)
                recyclerViewHome.adapter = adapter
            }
        }

        viewModel.loadExpenses(expenseDao)
    }
}
