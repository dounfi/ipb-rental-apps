package com.ipb.rental;

import java.io.Serializable;

public class AdminTransaksi implements Serializable {
    private String id;
    private String bookingId;
    private String itemName;
    private String itemCategory;
    private String penyewaName;
    private String penyewaEmail;
    private String ownerName;
    private String tanggalMulai;
    private String tanggalSelesai;
    private String durasi;
    private String status;
    private String metodePembayaran;
    private long totalHarga;
    private long dpAmount;
    private int imageRes;

    public AdminTransaksi(String id, String bookingId, String itemName, String itemCategory, String penyewaName, String penyewaEmail, String ownerName, String tanggalMulai, String tanggalSelesai, String durasi, String status, String metodePembayaran, long totalHarga, long dpAmount, int imageRes) {
        this.id = id;
        this.bookingId = bookingId;
        this.itemName = itemName;
        this.itemCategory = itemCategory;
        this.penyewaName = penyewaName;
        this.penyewaEmail = penyewaEmail;
        this.ownerName = ownerName;
        this.tanggalMulai = tanggalMulai;
        this.tanggalSelesai = tanggalSelesai;
        this.durasi = durasi;
        this.status = status;
        this.metodePembayaran = metodePembayaran;
        this.totalHarga = totalHarga;
        this.dpAmount = dpAmount;
        this.imageRes = imageRes;
    }

    // Getters and Setters
    public String getId() { return id; }
    public String getBookingId() { return bookingId; }
    public String getItemName() { return itemName; }
    public String getItemCategory() { return itemCategory; }
    public String getPenyewaName() { return penyewaName; }
    public String getPenyewaEmail() { return penyewaEmail; }
    public String getOwnerName() { return ownerName; }
    public String getTanggalMulai() { return tanggalMulai; }
    public String getTanggalSelesai() { return tanggalSelesai; }
    public String getDurasi() { return durasi; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getMetodePembayaran() { return metodePembayaran; }
    public long getTotalHarga() { return totalHarga; }
    public long getDpAmount() { return dpAmount; }
    public int getImageRes() { return imageRes; }
}
