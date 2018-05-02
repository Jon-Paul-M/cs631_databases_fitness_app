package edu.njit.cs631.fitness.web.model;

import edu.njit.cs631.fitness.validation.ValidPassword;

import javax.validation.constraints.NotNull;

public class PasswordModel {


    @NotNull
    private String oldPassword;

    @ValidPassword
    private String newPassword;

    @NotNull
    private String confirmNewPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }
}
