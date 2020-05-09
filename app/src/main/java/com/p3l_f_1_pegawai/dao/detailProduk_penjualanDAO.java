package com.p3l_f_1_pegawai.dao;

public class detailProduk_penjualanDAO {
    private String id_produk_tambah, nama_produk, satuan_produk, jenis_hewan;
    private Integer jumlah_produk;

    public detailProduk_penjualanDAO(String id_produk_tambah, String nama_produk, String satuan_produk, String jenis_hewan, Integer jumlah_produk) {
        this.id_produk_tambah = id_produk_tambah;
        this.nama_produk = nama_produk;
        this.satuan_produk = satuan_produk;
        this.jenis_hewan = jenis_hewan;
        this.jumlah_produk = jumlah_produk;
    }

    public String getId_produk_tambah() {
        return id_produk_tambah;
    }

    public void setId_produk_tambah(String id_produk_tambah) {
        this.id_produk_tambah = id_produk_tambah;
    }

    public String getNama_produk() {
        return nama_produk;
    }

    public void setNama_produk(String nama_produk) {
        this.nama_produk = nama_produk;
    }

    public String getSatuan_produk() {
        return satuan_produk;
    }

    public void setSatuan_produk(String satuan_produk) {
        this.satuan_produk = satuan_produk;
    }

    public String getJenis_hewan() {
        return jenis_hewan;
    }

    public void setJenis_hewan(String jenis_hewan) {
        this.jenis_hewan = jenis_hewan;
    }

    public Integer getJumlah_produk() {
        return jumlah_produk;
    }

    public void setJumlah_produk(Integer jumlah_produk) {
        this.jumlah_produk = jumlah_produk;
    }
}
