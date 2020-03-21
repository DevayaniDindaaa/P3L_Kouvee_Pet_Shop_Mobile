package com.p3l_f_1_pegawai.dao;

public class jenis_hewan {
    private String id_jenis_hewan, nama_jenis_hewan, status_data, time_stamp, keterangan;

    public jenis_hewan(String id_jenis_hewan, String nama_jenis_hewan, String status_data, String time_stamp, String keterangan) {
        this.id_jenis_hewan = id_jenis_hewan;
        this.nama_jenis_hewan = nama_jenis_hewan;
        this.status_data = status_data;
        this.time_stamp = time_stamp;
        this.keterangan = keterangan;
    }

    public String getId_jenis_hewan() {
        return id_jenis_hewan;
    }

    public void setId_jenis_hewan(String id_jenis_hewan) {
        this.id_jenis_hewan = id_jenis_hewan;
    }

    public String getNama_jenis_hewan() {
        return nama_jenis_hewan;
    }

    public void setNama_jenis_hewan(String nama_jenis_hewan) {
        this.nama_jenis_hewan = nama_jenis_hewan;
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
