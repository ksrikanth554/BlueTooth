package com.sritechsoftsolutions.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var bAdapter=BluetoothAdapter.getDefaultAdapter()
        bluetooth.isChecked=bAdapter.isEnabled

        bluetooth.setOnCheckedChangeListener { compoundButton, isChecked ->
           if (isChecked)
               bAdapter.enable()
            else
               bAdapter.disable()
        }
        getBlueTooth.setOnClickListener {
            var temp= mutableListOf<String>()
            var adapter=ArrayAdapter<String>(this@MainActivity,R.layout.abc_list_menu_item_checkbox,temp)
            lView.adapter=adapter
            bAdapter.startDiscovery()
            var iFilter=IntentFilter()
            iFilter.addAction(BluetoothDevice.ACTION_FOUND)
            registerReceiver(object :BroadcastReceiver(){
                override fun onReceive(context: Context?, intent: Intent?) {
                    var device=intent?.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)

                    temp.add(device?.name+"\n"+device?.address)
                    adapter.notifyDataSetChanged()
                }
            },iFilter)
        }
    }
}
