package com.p3l_f_1_pegawai.dao;

import org.json.JSONArray;

public class penjualan_layananDAO {
    private String no_transaksi_layanan, nama_hewan, nama_jenis_hewan, nama_ukuran_hewan, nama_konsumen, status_member, telepon_konsumen, nama_cs, nama_kasir, waktu_transaksi_layanan, status_pembayaran_layanan, status_pengerjaan_layanan;
    private Integer sub_total_layanan, diskon_layanan, total_pembayaran_layanan;
    private JSONArray detail_penjualan_layanan = new JSONArray();

    public penjualan_layananDAO(String no_transaksi_layanan, String nama_hewan, String nama_jenis_hewan, String nama_ukuran_hewan, String nama_konsumen, String status_member, String telepon_konsumen, String nama_cs, String nama_kasir, String waktu_transaksi_layanan, String status_pembayaran_layanan, String status_pengerjaan_layanan, Integer sub_total_layanan, Integer diskon_layanan, Integer total_pembayaran_layanan, JSONArray detail_penjualan_layanan) {
        this.no_transaksi_layanan = no_transaksi_layanan;
        this.nama_hewan = nama_hewan;
        this.nama_jenis_hewan = nama_jenis_hewan;
        this.nama_ukuran_hewan = nama_ukuran_hewan;
        this.nama_konsumen = nama_konsumen;
        this.status_member = status_member;
        this.telepon_konsumen = telepon_konsumen;
        this.nama_cs = nama_cs;
        this.nama_kasir = nama_kasir;
        this.waktu_transaksi_layanan = waktu_transaksi_layanan;
        this.status_pembayaran_layanan = status_pembayaran_layanan;
        this.status_pengerjaan_layanan = status_pengerjaan_layanan;
        this.sub_total_layanan = sub_total_layanan;
        this.diskon_layanan = diskon_layanan;
        this.total_pembayaran_layanan = total_pembayaran_layanan;
        this.detail_penjualan_layanan = detail_penjualan_layanan;
    }

    public String getNo_transaksi_layanan() {
        return no_transaksi_layanan;
    }

    public void setNo_transaksi_layanan(String no_transaksi_layanan) {
        this.no_transaksi_layanan = no_transaksi_layanan;
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

    public String getNama_ukuran_hewan() {
        return nama_ukuran_hewan;
    }

    public void setNama_ukuran_hewan(String nama_ukuran_hewan) {
        this.nama_ukuran_hewan = nama_ukuran_hewan;
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

    public String getTelepon_konsumen() {
        return telepon_konsumen;
    }

    public void setTelepon_konsumen(String telepon_konsumen) {
        this.telepon_konsumen = telepon_konsumen;
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

    public String getWaktu_transaksi_layanan() {
        return waktu_transaksi_layanan;
    }

    public void setWaktu_transaksi_layanan(String waktu_transaksi_layanan) {
        this.waktu_transaksi_layanan = waktu_transaksi_layanan;
    }

    public String getStatus_pembayaran_layanan() {
        return status_pembayaran_layanan;
    }

    public void setStatus_pembayaran_layanan(String status_pembayaran_layanan) {
        this.status_pembayaran_layanan = status_pembayaran_layanan;
    }

    public String getStatus_pengerjaan_layanan() {
        return status_pengerjaan_layanan;
    }

    public void setStatus_pengerjaan_layanan(String status_pengerjaan_layanan) {
        this.status_pengerjaan_layanan = status_pengerjaan_layanan;
    }

    public Integer getSub_total_layanan() {
        return sub_total_layanan;
    }

    public void setSub_total_layanan(Integer sub_total_layanan) {
        this.sub_total_layanan = sub_total_layanan;
    }

    public Integer getDiskon_layanan() {
        return diskon_layanan;
    }

    public void setDiskon_layanan(Integer diskon_layanan) {
        this.diskon_layanan = diskon_layanan;
    }

    public Integer getTotal_pembayaran_layanan() {
        return total_pembayaran_layanan;
    }

    public void setTotal_pembayaran_layanan(Integer total_pembayaran_layanan) {
        this.total_pembayaran_layanan = total_pembayaran_layanan;
    }

    public JSONArray getDetail_penjualan_layanan() {
        return detail_penjualan_layanan;
    }

    public void setDetail_penjualan_layanan(JSONArray detail_penjualan_layanan) {
        this.detail_penjualan_layanan = detail_penjualan_layanan;
    }
}