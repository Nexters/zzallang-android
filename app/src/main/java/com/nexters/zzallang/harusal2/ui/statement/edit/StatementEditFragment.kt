package com.nexters.zzallang.harusal2.ui.statement.edit

import android.app.DatePickerDialog
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.application.util.Constants
import com.nexters.zzallang.harusal2.base.BaseFragment
import com.nexters.zzallang.harusal2.databinding.FragmentEditStatementBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.ext.scope
import java.text.SimpleDateFormat
import java.util.*

class StatementEditFragment: BaseFragment<FragmentEditStatementBinding>() {
    override fun layoutRes(): Int = R.layout.fragment_edit_statement
    override val viewModel: StatementEditViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding.vm = viewModel
        binding.lifecycleOwner = this@StatementEditFragment
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.initData()
    }

    override fun bindingView() {
        super.bindingView()

        viewModel.setStatement(requireArguments().getLong("id"))

        binding.editStatementEditAmount.onFocusChangeListener =
            View.OnFocusChangeListener { v, isFocused ->
                if(isFocused) binding.layoutStatementEditKeypad.visibility = View.VISIBLE
                else binding.layoutStatementEditKeypad.visibility = View.GONE
            }

        binding.btnGroupStatementEdit.setOnCheckedChangeListener { radioGroup, id ->
            var type = Constants.STATEMENT_TYPE_DEFALT
            when(id){
                binding.btnEditTypeOut.id -> { type = Constants.STATEMENT_TYPE_OUT }
                binding.btnEditTypeIn.id -> { type = Constants.STATEMENT_TYPE_IN }
            }
            viewModel.setType(type)
        }

        binding.btnStatementEditKeypad.setOnClickListener {
            binding.editStatementEditAmount.clearFocus()
        }

        binding.btnStatementEditDone.setOnClickListener {
            GlobalScope.launch {
                viewModel.updateStatement()
            }
        }

        binding.layoutStatementEditDate.setOnClickListener {
            initDatePicker().show()
        }
    }

    fun initDatePicker(): DatePickerDialog {
        val cal = Calendar.getInstance()

        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, month, day ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, month)
                cal.set(Calendar.DAY_OF_MONTH, day)
                viewModel.setDate(SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault()).format(cal.time))
            }

        cal.time = viewModel.stringToDate(requireArguments().getString("date")?:viewModel.getDateForNow())
        val datePickerDialog = DatePickerDialog(requireContext(), R.style.DialogTheme, dateSetListener,
            cal.time.year,
            cal.time.month,
            cal.time.date
        )
        datePickerDialog.datePicker.minDate = viewModel.getMinDate()
        datePickerDialog.datePicker.maxDate = viewModel.getMaxDate()

        return datePickerDialog
    }
}