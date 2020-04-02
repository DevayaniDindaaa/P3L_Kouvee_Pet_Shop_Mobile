package com.p3l_f_1_pegawai.dao;

public class spinner_jenis_hewan {
    private String id_jenis_hewan, nama_jenis_hewan, status_data;

    public spinner_jenis_hewan(String id_jenis_hewan, String nama_jenis_hewan, String status_data) {
        this.id_jenis_hewan = id_jenis_hewan;
        this.nama_jenis_hewan = nama_jenis_hewan;
        this.status_data = status_data;
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
}
