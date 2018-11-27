package com.example.paul.homecooking.FragmentWelcome

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.paul.homecooking.Login
import com.example.paul.homecooking.R
import kotlinx.android.synthetic.main.frag_wel3.view.*

class Fragment_wel3 : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.frag_wel3,
                container, false)

        view.layoutWel3.setOnClickListener {

            val intent: Intent = Intent(context, Login::class.java)
            startActivity(intent)
        }
        return view
    }
}