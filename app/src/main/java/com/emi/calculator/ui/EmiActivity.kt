package com.emi.calculator.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.emi.calculator.R
import com.emi.calculator.common.Constants
import com.emi.calculator.common.Keyboard
import com.emi.calculator.databinding.EmiActivityBinding
import com.emi.calculator.extension.empty
import com.emi.calculator.utils.roundTo
import com.emi.calculator.viewmodel.EmiViewModel
import com.emi.calculator.viewmodel.EmiViewModelFactory
import com.emi.calculator.viewmodel.EmiActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class EmiActivity : AppCompatActivity() {
    private lateinit var binding: EmiActivityBinding
    private lateinit var emiViewModel: EmiViewModel
    private lateinit var viewModel: EmiActivityViewModel

    @Inject
    lateinit var emiViewModelFactory: EmiViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            viewModel = ViewModelProvider(this@EmiActivity).get(EmiActivityViewModel::class.java)
            setKeepVisibleCondition {
                viewModel.isLoading.value
            }
        }
        binding = DataBindingUtil.setContentView(this, R.layout.emi_activity)

        setUpView()
        setUpListener()
        setupCollectors()
    }

    private fun setUpView() {
        emiViewModel = ViewModelProvider(this, emiViewModelFactory).get(EmiViewModel::class.java)
        binding.viewModel = emiViewModel
        binding.lifecycleOwner = this
    }

    private fun setUpListener() {
        binding.btnCalculate.setOnClickListener {
            if (binding.btnCalculate.isEnabled) {
                emiViewModel.calculateEmi()

                emiViewModel.saveRecord()

                Keyboard.hide(this, binding.root)
            }
        }

        binding.btnReset.setOnClickListener {
            binding.txtViewEmiAmt.text = Constants.DEFAULT_VALUE
            binding.txtViewInterest.text = Constants.DEFAULT_VALUE
            binding.txtViewTotal.text = Constants.DEFAULT_VALUE

            binding.edTextAmount.text?.clear()
            binding.edTextRate.text?.clear()
            binding.edTextYear.text?.clear()
            binding.edTextMonth.text?.clear()

            binding.edTextAmount.requestFocus()
            Keyboard.hide(this, binding.root)
        }

        binding.textLastRecord.setOnClickListener {
            lifecycleScope.launch {
                emiViewModel.getRecord().collect {
                    binding.edTextAmount.setText(it.amount)
                    binding.edTextRate.setText(it.rate)
                    binding.edTextYear.setText(it.year)
                    binding.edTextMonth.setText(it.month)
                }
            }
        }
    }

    private fun setupCollectors() {
        lifecycleScope.launch {
            emiViewModel.isFormValid.collect {
                if (it) {
                    binding.btnCalculate.setBackgroundResource(R.drawable.button_style);
                } else {
                    binding.btnCalculate.setBackgroundResource(R.drawable.button_style_disable)
                }
                binding.btnCalculate.isEnabled = it
            }
        }

        lifecycleScope.launchWhenStarted {
            emiViewModel.uiSharedFlow.collect {
                binding.txtViewEmiAmt.text = String.roundTo(it.emi)
                binding.txtViewInterest.text = String.roundTo(it.interest)
                binding.txtViewTotal.text = String.roundTo(it.totalAmount)
            }
        }
    }

}