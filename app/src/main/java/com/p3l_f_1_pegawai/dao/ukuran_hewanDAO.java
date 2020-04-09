package com.p3l_f_1_pegawai.dao;

public class ukuran_hewanDAO {
    private String id_ukuran_hewan, nama_ukuran_hewan, status_data, time_stamp, keterangan;

    public ukuran_hewanDAO(String id_ukuran_hewan, String nama_ukuran_hewan, String status_data, String time_stamp, String keterangan) {
        this.id_ukuran_hewan = id_ukuran_hewan;
        this.nama_ukuran_hewan = nama_ukuran_hewan;
        this.status_data = status_data;
        this.time_stamp = time_stamp;
        this.keterangan = keterangan;
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
