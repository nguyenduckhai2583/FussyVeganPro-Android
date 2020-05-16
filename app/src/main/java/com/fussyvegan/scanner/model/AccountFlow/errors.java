package com.fussyvegan.scanner.model.AccountFlow;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class errors {
    @SerializedName("email")
    @Expose
    private email email;

    public errors() {
    }

    public errors(email email) {
        this.email = email;
    }

    public email getEmail() {
        return email;
    }

    public void setEmail(email email) {
        this.email = email;
    }
}
