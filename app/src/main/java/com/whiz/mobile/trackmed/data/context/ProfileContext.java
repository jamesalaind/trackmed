package com.whiz.mobile.trackmed.data.context;

/**
 * Created by whizk on 07/01/2016.
 */
public final class ProfileContext {
    private static ProfileContext instance;

    private ProfileContext() {
        this.profileName = "Wendell Wayne H. Estrada";
        this.companyName = "JeonSoft Corporation";
    }

    public static ProfileContext getInstance() {
        if (instance == null)
            instance = new ProfileContext();
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
