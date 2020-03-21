package com.p3l_f_1_pegawai.dao;

public class user {
    private String id_pegawai, id_role, nama_pegawai, username, tgl_lahir_pegawai, alamat_pegawai, no_telp;

    public user(String id_pegawai, String id_role, String nama_pegawai, String username, String tgl_lahir_pegawai, String alamat_pegawai, String no_telp) {
        this.id_pegawai = id_pegawai;
        this.id_role = id_role;
        this.nama_pegawai = nama_pegawai;
        this.username = username;
        this.tgl_lahir_pegawai = tgl_lahir_pegawai;
        this.alamat_pegawai = alamat_pegawai;
        this.no_telp = no_telp;
    }

    public String getId_pegawai() {
        return id_pegawai;
    }

    public String getTgl_lahir_pegawai() {
        return tgl_lahir_pegawai;
    }

    public void setTgl_lahir_pegawai(String tgl_lahir_pegawai) {
        this.tgl_lahir_pegawai = tgl_lahir_pegawai;
    }

    public String getAlamat_pegawai() {
        return alamat_pegawai;
    }

    public String getNo_telp() {
        return no_telp;
    }

    public void setNo_telp(String no_telp) {
        this.no_telp = no_telp;
    }

    public void setAlamat_pegawai(String alamat_pegawai) {
        this.alamat_pegawai = alamat_pegawai;
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
