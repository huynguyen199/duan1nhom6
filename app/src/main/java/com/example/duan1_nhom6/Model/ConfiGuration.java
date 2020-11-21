package com.example.duan1_nhom6.Model;

public class ConfiGuration {
    private String batterycapacity;
    private String screen;
    private String frontcamera;
    private String gpu;
    private String internalmemory;
    private String operatingsystem;
    private String ram;
    private String rearcamera;
    private String sim;

    public ConfiGuration() {
    }

    public ConfiGuration(String batterycapacity, String screen, String frontcamera, String gpu, String internalmemory,
                         String operatingsystem, String ram, String rearcamera, String sim) {
        this.batterycapacity = batterycapacity;
        this.screen = screen;
        this.frontcamera = frontcamera;
        this.gpu = gpu;
        this.internalmemory = internalmemory;
        this.operatingsystem = operatingsystem;
        this.ram = ram;
        this.rearcamera = rearcamera;
        this.sim = sim;
    }

    public String getBatterycapacity() {
        return batterycapacity;
    }

    public void setBatterycapacity(String batterycapacity) {
        this.batterycapacity = batterycapacity;
    }

    public String getScreen() {
        return screen;
    }

    public void setScreen(String screen) {
        this.screen = screen;
    }

    public String getFrontcamera() {
        return frontcamera;
    }

    public void setFrontcamera(String frontcamera) {
        this.frontcamera = frontcamera;
    }

    public String getGpu() {
        return gpu;
    }

    public void setGpu(String gpu) {
        this.gpu = gpu;
    }

    public String getInternalmemory() {
        return internalmemory;
    }

    public void setInternalmemory(String internalmemory) {
        this.internalmemory = internalmemory;
    }

    public String getOperatingsystem() {
        return operatingsystem;
    }

    public void setOperatingsystem(String operatingsystem) {
        this.operatingsystem = operatingsystem;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getRearcamera() {
        return rearcamera;
    }

    public void setRearcamera(String rearcamera) {
        this.rearcamera = rearcamera;
    }

    public String getSim() {
        return sim;
    }

    public void setSim(String sim) {
        this.sim = sim;
    }
}
