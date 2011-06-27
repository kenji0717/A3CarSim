package com.github.kenji0717.a3cs;

import jp.sourceforge.acerola3d.A23;

/**
 * CarBattleを実行するためのクラスです。mainメソッドしかありません。
 * これで起動されるアプリでは、シミュレーションが実行できる他、
 * プログラムの作成コンパイルもできます。
 */
public class CarBattle {
    /**
     * CarBattleを実行します。第1引数と第2引数でバトルさせたい
     * 2つのクラス名(パッケージ名も含めたもの)
     * を指定することができます。
     */
    public static void main(String args[]) {
        //本当は以下の行を有効にしてJavaアプリケーションでも
        //SecurityManagerを有効にしたいところだけど、
        //どうしても面倒なことになるのでコメントアウト。
        //信頼できないコードのシミュレーションをする場合は
        //JavaアプリケーションでなくJava Web Startで実行するべし。
        //if (System.getSecurityManager()==null)
        //    System.setSecurityManager(new SecurityManager());

        A23.setClassLoader(CarBattle.class.getClassLoader());
        new CarBattleImpl(args);
    }
}
