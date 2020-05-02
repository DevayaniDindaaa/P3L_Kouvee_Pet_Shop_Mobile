package com.p3l_f_1_pegawai.dao;

public class detail_penjualan_layananDAO {
    private String id_detail_trans_layanan, id_layanan, nama_layanan, nama_jenis_hewan, nama_ukuran_hewan, status_data, time_stamp, keterangan;
    private Integer harga_satuan_layanan, jumlah_layanan, jumlah_harga_layanan;

    public detail_penjualan_layananDAO(String id_detail_trans_layanan, String id_layanan, String nama_layanan, String nama_jenis_hewan, String nama_ukuran_hewan, String status_data, String time_stamp, String keterangan, Integer harga_satuan_layanan, Integer jumlah_layanan, Integer jumlah_harga_layanan) {
        this.id_detail_trans_layanan = id_detail_trans_layanan;
        this.id_layanan = id_layanan;
        this.nama_layanan = nama_layanan;
        this.nama_jenis_hewan = nama_jenis_hewan;
        this.nama_ukuran_hewan = nama_ukuran_hewan;
        this.status_data = status_data;
        this.time_stamp = time_stamp;
        this.keterangan = keterangan;
        this.harga_satuan_layanan = harga_satuan_layanan;
        this.jumlah_layanan = jumlah_layanan;
        this.jumlah_harga_layanan = jumlah_harga_layanan;
    }

    public String getId_detail_trans_layanan() {
        return id_detail_trans_layanan;
    }

    public void setId_detail_trans_layanan(String id_detail_trans_layanan) {
        this.id_detail_trans_layanan = id_detail_trans_layanan;
    }

    public String getId_layanan() {
        return id_layanan;
    }

    public void setId_layanan(String id_layanan) {
        this.id_layanan = id_layanan;
    }

    public String getNama_layanan() {
        return nama_layanan;
    }

    public void setNama_layanan(String nama_layanan) {
        this.nama_layanan = nama_layanan;
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

    public Integer getHarga_satuan_layanan() {
        return harga_satuan_layanan;
    }

    public void setHarga_satuan_layanan(Integer harga_satuan_layanan) {
        this.harga_satuan_layanan = harga_satuan_layanan;
    }

    public Integer getJumlah_layanan() {
        return jumlah_layanan;
    }

    public void setJumlah_layanan(Integer jumlah_layanan) {
        this.jumlah_layanan = jumlah_layanan;
    }

    public Integer getJumlah_harga_layanan() {
        return jumlah_harga_layanan;
    }

    public void setJumlah_harga_layanan(Integer jumlah_harga_layanan) {
        this.jumlah_harga_layanan = jumlah_harga_layanan;
    }
}
