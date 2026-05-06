package com.ipb.rental;

import java.io.Serializable;

public class AdminPengaduan implements Serializable {
    private String id;
    private String kategori;
    private String pelaporName;
    private String pelaporEmail;
    private String itemName;
    private String isiPengaduan;
    private String status;
    private String waktu;

    public AdminPengaduan(String id, String kategori, String pelaporName, String pelaporEmail, String itemName, String isiPengaduan, String status, String waktu) {
        this.id = id;
        this.kategori = kategori;
        this.pelaporName = pelaporName;
        this.pelaporEmail = pelaporEmail;
        this.itemName = itemName;
        this.isiPengaduan = isiPengaduan;
        this.status = status;
        this.waktu = waktu;
    }

    public String getId() { return id; }
    public String getKategori() { return kategori; }
    public String getPelaporName() { return pelaporName; }
    public String getPelaporEmail() { return pelaporEmail; }
    public String getItemName() { return itemName; }
    public String getIsiPengaduan() { return isiPengaduan; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getWaktu() { return waktu; }
}
