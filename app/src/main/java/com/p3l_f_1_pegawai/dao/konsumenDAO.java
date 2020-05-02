package com.p3l_f_1_pegawai.dao;

import org.json.JSONArray;

public class konsumenDAO {
    private String id_konsumen, nama_konsumen, alamat_konsumen, tgl_lahir_konsumen, no_tlp_konsumen, status_member, status_data, time_stamp, keterangan;
    private JSONArray detail_hewankonsumen = new JSONArray();

    public konsumenDAO(String id_konsumen, String nama_konsumen, String alamat_konsumen, String tgl_lahir_konsumen, String no_tlp_konsumen, String status_member, String status_data, String time_stamp, String keterangan, JSONArray detail_hewankonsumen) {
        this.id_konsumen = id_konsumen;
        this.nama_konsumen = nama_konsumen;
        this.alamat_konsumen = alamat_konsumen;
        this.tgl_lahir_konsumen = tgl_lahir_konsumen;
        this.no_tlp_konsumen = no_tlp_konsumen;
        this.status_member = status_member;
        this.status_data = status_data;
        this.time_stamp = time_stamp;
        this.keterangan = keterangan;
        this.detail_hewankonsumen = detail_hewankonsumen;
    }

    public JSONArray getDetail_hewankonsumen() {
        return detail_hewankonsumen;
    }

    public void setDetail_hewankonsumen(JSONArray detail_hewankonsumen) {
        this.detail_hewankonsumen = detail_hewankonsumen;
    }

    public String getId_konsumen() {
        return id_konsumen;
    }

    public void setId_konsumen(String id_konsumen) {
        this.id_konsumen = id_konsumen;
    }

    public String getNama_konsumen() {
        return nama_konsumen;
    }

    public void setNama_konsumen(String nama_konsumen) {
        this.nama_konsumen = nama_konsumen;
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
