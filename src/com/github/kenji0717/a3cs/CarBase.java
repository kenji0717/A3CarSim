package com.github.kenji0717.a3cs;

import javax.vecmath.*;

/**
 * このクラスを拡張してレースorバトルするための車を作成します。
 * これを拡張して作成する車クラスは必ず引数無しの
 * コンストラクタを持つ必要があります。
 */
public abstract class CarBase {
    void init(Vector3d loc,Vector3d rot,String a3url) {
        ;
    }
    protected abstract void exec();
}
