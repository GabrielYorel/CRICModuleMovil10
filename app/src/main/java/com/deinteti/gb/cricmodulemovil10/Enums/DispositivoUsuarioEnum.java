package com.deinteti.gb.cricmodulemovil10.Enums;

import com.google.gson.annotations.SerializedName;

/**
 * Created by desarrollo on 08/03/2018.
 */

public enum DispositivoUsuarioEnum {
    @SerializedName("1")ACTIVE,
    @SerializedName("2")WAITPERMISSION,
    @SerializedName("3")LOCKED,
    @SerializedName("4")REJECTED,
    @SerializedName("5")BLACKLIST,
    @SerializedName("6")ERROR_REG,
    @SerializedName("7")REVOCADO
}

/**
 * ACTIVE = 1,
 WAITPERMISSION = 2,
 LOCKED = 3,
 REJECTED = 4,
 INCORRECT = 5,
 DELETED = 5,
 REVOCADO = 6
 */