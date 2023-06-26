package com.shaikhsoheb.dnsandblescanner.adapters

import android.annotation.SuppressLint
import android.bluetooth.le.ScanResult
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shaikhsoheb.dnsandblescanner.R
import com.shaikhsoheb.dnsandblescanner.databinding.BleScanResultItemBinding

class ScanResultAdapter(
    private val items: List<ScanResult>,
    private val onClickListener: ((device: ScanResult) -> Unit)
) : RecyclerView.Adapter<ScanResultAdapter.ViewHolder>() {

    lateinit var bleBinding: BleScanResultItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.ble_scan_result_item, parent)
        return ViewHolder(view, onClickListener)
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

        lateinit var binding: BleScanResultItemBinding

        @SuppressLint("MissingPermission")
        fun bind(result: ScanResult) {
            binding = BleScanResultItemBinding.bind(view)
            binding.tvDeviceName.text = result.device.name ?: "Unnamed"
            binding.tvMacAddress.text = result.device.address
            binding.tvSignalStrength.text = "${result.rssi} dBm"
            binding.root.setOnClickListener { onClickListener.invoke(result) }
        }
    }
}