package com.p3l_f_1_pegawai.dao;

public class detail_penjualan_produkDAO {
    private String id_detail_trans_produk, id_produk, nama_produk, satuan_produk, status_data, time_stamp, keterangan;
    private Integer harga_satuan_produk, jumlah_produk, jumlah_harga_produk;

    public detail_penjualan_produkDAO(String id_detail_trans_produk, String id_produk, String nama_produk, String satuan_produk, String status_data, String time_stamp, String keterangan, Integer harga_satuan_produk, Integer jumlah_produk, Integer jumlah_harga_produk) {
        this.id_detail_trans_produk = id_detail_trans_produk;
        this.id_produk = id_produk;
        this.nama_produk = nama_produk;
        this.satuan_produk = satuan_produk;
        this.status_data = status_data;
        this.time_stamp = time_stamp;
        this.keterangan = keterangan;
        this.harga_satuan_produk = harga_satuan_produk;
        this.jumlah_produk = jumlah_produk;
        this.jumlah_harga_produk = jumlah_harga_produk;
    }

    public String getId_detail_trans_produk() {
        return id_detail_trans_produk;
    }

    public void setId_detail_trans_produk(String id_detail_trans_produk) {
        this.id_detail_trans_produk = id_detail_trans_produk;
    }

    public String getId_produk() {
        return id_produk;
    }

    public void setId_produk(String id_produk) {
        this.id_produk = id_produk;
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

    public Integer getHarga_satuan_produk() {
        return harga_satuan_produk;
    }

    public void setHarga_satuan_produk(Integer harga_satuan_produk) {
        this.harga_satuan_produk = harga_satuan_produk;
    }

    public Integer getJumlah_produk() {
        return jumlah_produk;
    }

    public void setJumlah_produk(Integer jumlah_produk) {
        this.jumlah_produk = jumlah_produk;
    }

    public Integer getJumlah_harga_produk() {
        return jumlah_harga_produk;
    }

    public void setJumlah_harga_produk(Integer jumlah_harga_produk) {
        this.jumlah_harga_produk = jumlah_harga_produk;
    }
}
