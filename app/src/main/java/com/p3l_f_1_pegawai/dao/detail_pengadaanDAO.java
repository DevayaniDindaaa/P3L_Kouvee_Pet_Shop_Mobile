package com.p3l_f_1_pegawai.dao;

public class detail_pengadaanDAO {
    private String id_detail_pengadaan, nama_produk, satuan_produk, status_data, time_stamp, keterangan;
    private Integer jumlah_produk_dipesan;

    public detail_pengadaanDAO(String id_detail_pengadaan, String nama_produk, String satuan_produk, String status_data, String time_stamp, String keterangan, Integer jumlah_produk_dipesan) {
        this.id_detail_pengadaan = id_detail_pengadaan;
        this.nama_produk = nama_produk;
        this.satuan_produk = satuan_produk;
        this.status_data = status_data;
        this.time_stamp = time_stamp;
        this.keterangan = keterangan;
        this.jumlah_produk_dipesan = jumlah_produk_dipesan;
    }

    public String getId_detail_pengadaan() {
        return id_detail_pengadaan;
    }

    public void setId_detail_pengadaan(String id_detail_pengadaan) {
        this.id_detail_pengadaan = id_detail_pengadaan;
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

    public String getStatus_data() {
        return status_data;
    }

    public void setStatus_data(String status_data) {
        this.status_data = status_data;
    }

    public String getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(String time_stamp) {
        this.time_stamp = time_stamp;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public Integer getJumlah_produk_dipesan() {
        return jumlah_produk_dipesan;
    }

    public void setJumlah_produk_dipesan(Integer jumlah_produk_dipesan) {
        this.jumlah_produk_dipesan = jumlah_produk_dipesan;
    }
}
