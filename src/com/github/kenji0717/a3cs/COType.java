package com.github.kenji0717.a3cs;

enum COType {
    DYNAMIC,
    STATIC,
    KINEMATIC,
    GHOST,

    /** DYNAMICの物の座標などを変更するために一時的に変更するためにKINEMATICになっている状態 */
    KINEMATIC_TEMP
}
