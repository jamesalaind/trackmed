package com.jeonsoft.mobile.essbuddy.data;

/**
 * Created by whizk on 07/01/2016.
 */
public final class ProfileManager {
    private static ProfileManager instance;

    private ProfileManager() {
        this.profileName = "Wendell Wayne H. Estrada";
        this.companyName = "JeonSoft Corporation";
    }

    public static ProfileManager getInstance() {
        if (instance == null)
            instance = new ProfileManager();
        return instance;
    }

    private String profileName;
    private String companyName;

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
