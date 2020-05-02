package com.p3l_f_1_pegawai.dao;

public class hewanDAO {
    private String id_hewan, id_jenis_hewan, id_ukuran_hewan, id_konsumen, alamat_konsumen, tgl_lahir_konsumen, no_tlp_konsumen, status_member, nama_hewan, tgl_lahir_hewan, status_data, time_stamp, keterangan;

    public hewanDAO(String id_hewan, String id_jenis_hewan, String id_ukuran_hewan, String id_konsumen, String alamat_konsumen, String tgl_lahir_konsumen, String no_tlp_konsumen, String status_member, String nama_hewan, String tgl_lahir_hewan, String status_data, String time_stamp, String keterangan) {
        this.id_hewan = id_hewan;
        this.id_jenis_hewan = id_jenis_hewan;
        this.id_ukuran_hewan = id_ukuran_hewan;
        this.id_konsumen = id_konsumen;
        this.alamat_konsumen = alamat_konsumen;
        this.tgl_lahir_konsumen = tgl_lahir_konsumen;
        this.no_tlp_konsumen = no_tlp_konsumen;
        this.status_member = status_member;
        this.nama_hewan = nama_hewan;
        this.tgl_lahir_hewan = tgl_lahir_hewan;
        this.status_data = status_data;
        this.time_stamp = time_stamp;
        this.keterangan = keterangan;
    }

    public String getId_hewan() {
        return id_hewan;
    }

    public void setId_hewan(String id_hewan) {
        this.id_hewan = id_hewan;
    }

    public String getId_jenis_hewan() {
        return id_jenis_hewan;
    }

    public void setId_jenis_hewan(String id_jenis_hewan) {
        this.id_jenis_hewan = id_jenis_hewan;
    }

    public String getId_ukuran_hewan() {
        return id_ukuran_hewan;
    }

    public void setId_ukuran_hewan(String id_ukuran_hewan) {
        this.id_ukuran_hewan = id_ukuran_hewan;
    }

    public String getId_konsumen() {
        return id_konsumen;
    }

    public void setId_konsumen(String id_konsumen) {
        this.id_konsumen = id_konsumen;
    }

    public String getAlamat_konsumen() {
        return alamat_konsumen;
    }

    public void setAlamat_konsumen(String alamat_konsumen) {
        this.alamat_konsumen = alamat_konsumen;
    }

    public String getTgl_lahir_konsumen() {
        return tgl_lahir_konsumen;
    }

    public void setTgl_lahir_konsumen(String tgl_lahir_konsumen) {
        this.tgl_lahir_konsumen = tgl_lahir_konsumen;
    }

    public String getNo_tlp_konsumen() {
        return no_tlp_konsumen;
    }

    public void setNo_tlp_konsumen(String no_tlp_konsumen) {
        this.no_tlp_konsumen = no_tlp_konsumen;
    }

    public String getStatus_member() {
        return status_member;
    }

    public void setStatus_member(String status_member) {
        this.status_member = status_member;
    }

    public String getNama_hewan() {
        return nama_hewan;
    }

    public void setNama_hewan(String nama_hewan) {
        this.nama_hewan = nama_hewan;
    }

    public String getTgl_lahir_hewan() {
        return tgl_lahir_hewan;
    }

    public void setTgl_lahir_hewan(String tgl_lahir_hewan) {
        this.tgl_lahir_hewan = tgl_lahir_hewan;
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