package com.p3l_f_1_pegawai.dao;

public class supplier {
    private String id_supplier, nama_supplier, alamat_supplier, kota_supplier, telepon_supplier, status_data, time_stamp, keterangan;

    public supplier(String id_supplier, String nama_supplier, String alamat_supplier, String kota_supplier, String telepon_supplier, String status_data, String time_stamp, String keterangan) {
        this.id_supplier = id_supplier;
        this.nama_supplier = nama_supplier;
        this.alamat_supplier = alamat_supplier;
        this.kota_supplier = kota_supplier;
        this.telepon_supplier = telepon_supplier;
        this.status_data = status_data;
        this.time_stamp = time_stamp;
        this.keterangan = keterangan;
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

    public String getTelepon_supplier() {
        return telepon_supplier;
    }

    public void setTelepon_supplier(String telepon_supplier) {
        this.telepon_supplier = telepon_supplier;
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
}
