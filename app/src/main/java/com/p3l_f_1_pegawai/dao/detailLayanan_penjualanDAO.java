package com.p3l_f_1_pegawai.dao;

public class detailLayanan_penjualanDAO {
    private String id_layanan_tambah, nama_layanan;
    private Integer jumlah_layanan;

    public detailLayanan_penjualanDAO(String id_layanan_tambah, String nama_layanan, Integer jumlah_layanan) {
        this.id_layanan_tambah = id_layanan_tambah;
        this.nama_layanan = nama_layanan;
        this.jumlah_layanan = jumlah_layanan;
    }

    public String getId_layanan_tambah() {
        return id_layanan_tambah;
    }

    public void setId_layanan_tambah(String id_layanan_tambah) {
        this.id_layanan_tambah = id_layanan_tambah;
    }

    public String getNama_layanan() {
        return nama_layanan;
    }

    public void setNama_layanan(String nama_layanan) {
        this.nama_layanan = nama_layanan;
    }

    public Integer getJumlah_layanan() {
        return jumlah_layanan;
    }

    public void setJumlah_layanan(Integer jumlah_layanan) {
        this.jumlah_layanan = jumlah_layanan;
    }
}
