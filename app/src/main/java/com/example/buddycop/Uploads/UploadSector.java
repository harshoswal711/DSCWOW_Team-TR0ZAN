package com.example.buddycop.Uploads;

public class UploadSector {
    String sectorName, sectorHeadName, lat, lan;

    public UploadSector() {
    }

    public UploadSector(String sectorName, String sectorHeadName, String lat, String lan) {
        this.sectorName = sectorName;
        this.sectorHeadName = sectorHeadName;
    }

    public String getSectorName() {
        return sectorName;
    }

    public void setSectorName(String sectorName) {
        this.sectorName = sectorName;
    }

    public String getSectorHeadName() {
        return sectorHeadName;
    }

    public void setSectorHeadName(String sectorHeadName) {
        this.sectorHeadName = sectorHeadName;
    }

    @Override
    public String toString() {
        return sectorName;
    }
}
