package com.shaikhsoheb.dnsandblescanner.adapters

import android.annotation.SuppressLint
import android.bluetooth.le.ScanResult
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shaikhsoheb.dnsandblescanner.R
import com.shaikhsoheb.dnsandblescanner.databinding.BleScanResultItemBinding

class ScanResultAdapter(
    private val items: List<ScanResult>,
    private val onClickListener: ((device: ScanResult) -> Unit)
) : RecyclerView.Adapter<ScanResultAdapter.ViewHolder>() {

    lateinit var bleBinding: BleScanResultItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        bleBinding = BleScanResultItemBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(bleBinding.root, onClickListener)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    class ViewHolder(
        private val view: View,
        private val onClickListener: ((device: ScanResult) -> Unit)
    ) : RecyclerView.ViewHolder(view) {

        @SuppressLint("MissingPermission")
        fun bind(result: ScanResult) {
            view.findViewById<TextView>(R.id.tv_device_name).text =
                result.device.name ?: "Unnamed"

            view.findViewById<TextView>(R.id.tv_mac_address).text = result.device.address
            view.findViewById<TextView>(R.id.tv_signal_strength).text = "${result.rssi} dBm"
            view.setOnClickListener { onClickListener.invoke(result) }
        }
    }
}