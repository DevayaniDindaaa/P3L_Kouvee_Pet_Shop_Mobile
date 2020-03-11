package com.p3l_f_1_pegawai.dao;

public class user {
    private String id_pegawai, id_role, nama_pegawai, username;

    public user(String id_pegawai, String id_role, String nama_pegawai, String username) {
        this.id_pegawai = id_pegawai;
        this.id_role = id_role;
        this.nama_pegawai = nama_pegawai;
        this.username = username;
    }

    public String getId_pegawai() {
        return id_pegawai;
    }

    public void setId_pegawai(String id_pegawai) {
        this.id_pegawai = id_pegawai;
    }

    public String getId_role() {
        return id_role;
    }

    public void setId_role(String id_role) {
        this.id_role = id_role;
    }

    public String getNama_pegawai() {
        return nama_pegawai;
    }

    public void setNama_pegawai(String nama_pegawai) {
        this.nama_pegawai = nama_pegawai;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
