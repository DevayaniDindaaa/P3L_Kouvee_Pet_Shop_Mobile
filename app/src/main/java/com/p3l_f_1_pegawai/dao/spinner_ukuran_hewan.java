package com.p3l_f_1_pegawai.dao;

public class spinner_ukuran_hewan {
    private String id_ukuran_hewan, nama_ukuran_hewan, status_data;

    public spinner_ukuran_hewan(String id_ukuran_hewan, String nama_ukuran_hewan, String status_data) {
        this.id_ukuran_hewan = id_ukuran_hewan;
        this.nama_ukuran_hewan = nama_ukuran_hewan;
        this.status_data = status_data;
    }

    public String getId_ukuran_hewan() {
        return id_ukuran_hewan;
    }

    public void setId_ukuran_hewan(String id_ukuran_hewan) {
        this.id_ukuran_hewan = id_ukuran_hewan;
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
}
