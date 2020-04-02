package com.p3l_f_1_pegawai.dao;

public class produk {
    private String id_produk, nama_jenis_hewan, nama_produk, satuan_produk, foto_produk, status_data, time_stamp, keterangan;
    private Integer stok_produk, stok_minimal_produk, harga_produk;

    public produk(String id_produk, String nama_jenis_hewan, String nama_produk, String satuan_produk, String foto_produk, String status_data, String time_stamp, String keterangan, Integer stok_produk, Integer stok_minimal_produk, Integer harga_produk) {
        this.id_produk = id_produk;
        this.nama_jenis_hewan = nama_jenis_hewan;
        this.nama_produk = nama_produk;
        this.satuan_produk = satuan_produk;
        this.foto_produk = foto_produk;
        this.status_data = status_data;
        this.time_stamp = time_stamp;
        this.keterangan = keterangan;
        this.stok_produk = stok_produk;
        this.stok_minimal_produk = stok_minimal_produk;
        this.harga_produk = harga_produk;
    }

    public String getId_produk() {
        return id_produk;
    }

    public void setId_produk(String id_produk) {
        this.id_produk = id_produk;
    }

    public String getNama_jenis_hewan() {
        return nama_jenis_hewan;
    }

    public void setNama_jenis_hewan(String nama_jenis_hewan) {
        this.nama_jenis_hewan = nama_jenis_hewan;
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

    public String getFoto_produk() {
        return foto_produk;
    }

    public void setFoto_produk(String foto_produk) {
        this.foto_produk = foto_produk;
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

    public Integer getStok_produk() {
        return stok_produk;
    }

    public void setStok_produk(Integer stok_produk) {
        this.stok_produk = stok_produk;
    }

    public Integer getStok_minimal_produk() {
        return stok_minimal_produk;
    }

    public void setStok_minimal_produk(Integer stok_minimal_produk) {
        this.stok_minimal_produk = stok_minimal_produk;
    }

    public Integer getHarga_produk() {
        return harga_produk;
    }

    public void setHarga_produk(Integer harga_produk) {
        this.harga_produk = harga_produk;
    }
}
