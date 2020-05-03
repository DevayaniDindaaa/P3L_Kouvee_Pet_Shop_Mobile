package com.p3l_f_1_pegawai.dao;

import org.json.JSONArray;

public class penjualan_produkDAO {
    private String no_transaksi_produk, nama_hewan, nama_jenis_hewan, nama_konsumen, status_member, nama_cs, nama_kasir, waktu_transaksi_produk, status_pembayaran_produk;
    private Integer sub_total_produk, diskon_produk, total_pembayaran_produk;
    private JSONArray detail_penjualan_produk = new JSONArray();

    public penjualan_produkDAO(String no_transaksi_produk, String nama_hewan, String nama_jenis_hewan, String nama_konsumen, String status_member, String nama_cs, String nama_kasir, String waktu_transaksi_produk, String status_pembayaran_produk, Integer sub_total_produk, Integer diskon_produk, Integer total_pembayaran_produk, JSONArray detail_penjualan_produk) {
        this.no_transaksi_produk = no_transaksi_produk;
        this.nama_hewan = nama_hewan;
        this.nama_jenis_hewan = nama_jenis_hewan;
        this.nama_konsumen = nama_konsumen;
        this.status_member = status_member;
        this.nama_cs = nama_cs;
        this.nama_kasir = nama_kasir;
        this.waktu_transaksi_produk = waktu_transaksi_produk;
        this.status_pembayaran_produk = status_pembayaran_produk;
        this.sub_total_produk = sub_total_produk;
        this.diskon_produk = diskon_produk;
        this.total_pembayaran_produk = total_pembayaran_produk;
        this.detail_penjualan_produk = detail_penjualan_produk;
    }

    public String getNo_transaksi_produk() {
        return no_transaksi_produk;
    }

    public void setNo_transaksi_produk(String no_transaksi_produk) {
        this.no_transaksi_produk = no_transaksi_produk;
    }

    public String getNama_hewan() {
        return nama_hewan;
    }

    public void setNama_hewan(String nama_hewan) {
        this.nama_hewan = nama_hewan;
    }

    public String getNama_jenis_hewan() {
        return nama_jenis_hewan;
    }

    public void setNama_jenis_hewan(String nama_jenis_hewan) {
        this.nama_jenis_hewan = nama_jenis_hewan;
    }

    public String getNama_konsumen() {
        return nama_konsumen;
    }

    public void setNama_konsumen(String nama_konsumen) {
        this.nama_konsumen = nama_konsumen;
    }

    public String getStatus_member() {
        return status_member;
    }

    public void setStatus_member(String status_member) {
        this.status_member = status_member;
    }

    public String getNama_cs() {
        return nama_cs;
    }

    public void setNama_cs(String nama_cs) {
        this.nama_cs = nama_cs;
    }

    public String getNama_kasir() {
        return nama_kasir;
    }

    public void setNama_kasir(String nama_kasir) {
        this.nama_kasir = nama_kasir;
    }

    public String getWaktu_transaksi_produk() {
        return waktu_transaksi_produk;
    }

    public void setWaktu_transaksi_produk(String waktu_transaksi_produk) {
        this.waktu_transaksi_produk = waktu_transaksi_produk;
    }

    public String getStatus_pembayaran_produk() {
        return status_pembayaran_produk;
    }

    public void setStatus_pembayaran_produk(String status_pembayaran_produk) {
        this.status_pembayaran_produk = status_pembayaran_produk;
    }

    public Integer getSub_total_produk() {
        return sub_total_produk;
    }

    public void setSub_total_produk(Integer sub_total_produk) {
        this.sub_total_produk = sub_total_produk;
    }

    public Integer getDiskon_produk() {
        return diskon_produk;
    }

    public void setDiskon_produk(Integer diskon_produk) {
        this.diskon_produk = diskon_produk;
    }

    public Integer getTotal_pembayaran_produk() {
        return total_pembayaran_produk;
    }

    public void setTotal_pembayaran_produk(Integer total_pembayaran_produk) {
        this.total_pembayaran_produk = total_pembayaran_produk;
    }

    public JSONArray getDetail_penjualan_produk() {
        return detail_penjualan_produk;
    }

    public void setDetail_penjualan_produk(JSONArray detail_penjualan_produk) {
        this.detail_penjualan_produk = detail_penjualan_produk;
    }
}
