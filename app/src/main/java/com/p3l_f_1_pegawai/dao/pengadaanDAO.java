package com.p3l_f_1_pegawai.dao;

import org.json.JSONArray;

public class pengadaanDAO {
    private String nomor_pemesanan, id_supplier, nama_supplier, alamat_supplier, kota_supplier, no_tlp_supplier, tgl_pemesanan, tgl_cetak_surat, status_cetak_surat, status_kedatangan_produk;
    private JSONArray detail_pengadaan = new JSONArray();

    public pengadaanDAO(String nomor_pemesanan, String id_supplier, String nama_supplier, String alamat_supplier, String kota_supplier, String no_tlp_supplier, String tgl_pemesanan, String tgl_cetak_surat, String status_cetak_surat, String status_kedatangan_produk, JSONArray detail_pengadaan) {
        this.nomor_pemesanan = nomor_pemesanan;
        this.id_supplier = id_supplier;
        this.nama_supplier = nama_supplier;
        this.alamat_supplier = alamat_supplier;
        this.kota_supplier = kota_supplier;
        this.no_tlp_supplier = no_tlp_supplier;
        this.tgl_pemesanan = tgl_pemesanan;
        this.tgl_cetak_surat = tgl_cetak_surat;
        this.status_cetak_surat = status_cetak_surat;
        this.status_kedatangan_produk = status_kedatangan_produk;
        this.detail_pengadaan = detail_pengadaan;
    }

    public String getNomor_pemesanan() {
        return nomor_pemesanan;
    }

    public void setNomor_pemesanan(String nomor_pemesanan) {
        this.nomor_pemesanan = nomor_pemesanan;
    }

    public String getId_supplier() {
        return id_supplier;
    }

    public void setId_supplier(String id_supplier) {
        this.id_supplier = id_supplier;
    }

    public String getNama_supplier() {
        return nama_supplier;
    }

    public void setNama_supplier(String nama_supplier) {
        this.nama_supplier = nama_supplier;
    }

    public String getAlamat_supplier() {
        return alamat_supplier;
    }

    public void setAlamat_supplier(String alamat_supplier) {
        this.alamat_supplier = alamat_supplier;
    }

    public String getKota_supplier() {
        return kota_supplier;
    }

    public void setKota_supplier(String kota_supplier) {
        this.kota_supplier = kota_supplier;
    }

    public String getNo_tlp_supplier() {
        return no_tlp_supplier;
    }

    public void setNo_tlp_supplier(String no_tlp_supplier) {
        this.no_tlp_supplier = no_tlp_supplier;
    }

    public String getTgl_pemesanan() {
        return tgl_pemesanan;
    }

    public void setTgl_pemesanan(String tgl_pemesanan) {
        this.tgl_pemesanan = tgl_pemesanan;
    }

    public String getTgl_cetak_surat() {
        return tgl_cetak_surat;
    }

    public void setTgl_cetak_surat(String tgl_cetak_surat) {
        this.tgl_cetak_surat = tgl_cetak_surat;
    }

    public String getStatus_cetak_surat() {
        return status_cetak_surat;
    }

    public void setStatus_cetak_surat(String status_cetak_surat) {
        this.status_cetak_surat = status_cetak_surat;
    }

    public String getStatus_kedatangan_produk() {
        return status_kedatangan_produk;
    }

    public void setStatus_kedatangan_produk(String status_kedatangan_produk) {
        this.status_kedatangan_produk = status_kedatangan_produk;
    }

    public JSONArray getDetail_pengadaan() {
        return detail_pengadaan;
    }

    public void setDetail_pengadaan(JSONArray detail_pengadaan) {
        this.detail_pengadaan = detail_pengadaan;
    }
}
