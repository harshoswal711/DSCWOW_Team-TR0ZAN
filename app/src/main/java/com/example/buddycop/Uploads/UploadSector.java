package com.example.wowhack.Uploads;

public class UploadSector {
    String sectorName, sectorHeadName;

    public UploadSector() {
    }

    public UploadSector(String sectorName, String sectorHeadName) {
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
