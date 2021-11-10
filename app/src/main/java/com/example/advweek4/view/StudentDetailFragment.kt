package com.example.advweek4.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.advweek4.R
import com.example.advweek4.databinding.FragmentStudentDetailBinding
import com.example.advweek4.model.Student
import com.example.advweek4.util.loadImage
import com.example.advweek4.viewmodel.DetailViewModel
import com.example.advweek4.viewmodel.ListViewModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_student_detail.*
import kotlinx.android.synthetic.main.fragment_student_list.*
import java.util.concurrent.TimeUnit

class StudentDetailFragment : Fragment(),ButtonNotifClickListener {
    private lateinit var  viewModel:DetailViewModel
    private lateinit var dataBinding:FragmentStudentDetailBinding
    private var stdn=Student("","","","","")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate<FragmentStudentDetailBinding>(inflater, R.layout.fragment_student_detail, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = StudentDetailFragmentArgs.fromBundle(requireArguments()).id
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        viewModel.fetch(id)
        observeViewModel()
        dataBinding.btnNoftifListener=this
    }
    private fun observeViewModel() {
        viewModel.studentLD.observe(viewLifecycleOwner, Observer {
//            with(it){
//                imgStudentDetail.loadImage(photoUrl.toString(),progressBarDetil)
//                Picasso.get().load(photoUrl.toString())
//                    .resize(400, 400)
//                    .centerCrop()
//                    .error(R.drawable.ic_baseline_error_24)
//                    .into(imgStudentDetail)
//                txtId.setText(id)
//                txtBoD.setText(bod)
//                txtName.setText(name)
//                txtPhone.setText(phone)
//            }
            dataBinding.student=it
        })
    }

    override fun onButtonNotifClick(v: View) {
        Observable.timer(5, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.d("Messages", "five seconds")
                MainActivity.showNotification(v.tag.toString(),
                    "A new notification created",
                    R.drawable.ic_baseline_account_circle_24)
            }
    }
}