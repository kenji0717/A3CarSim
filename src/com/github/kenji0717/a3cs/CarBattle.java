package com.github.kenji0717.a3cs;

import java.net.URL;
import java.net.URLClassLoader;
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
        System.setSecurityManager(null);//gahaここは後で要確認
        ClassLoader cl = CarBattle.class.getClassLoader();
        A23.setClassLoader(cl);
        if (cl instanceof URLClassLoader) {
            URL urls[] = ((URLClassLoader)cl).getURLs();
            for (URL url:urls) {
                System.out.println(url.toString());
            }
        }
        System.out.println("gaha:classLoader:"+(cl.toString()));
        URL url = cl.getResource("res/stk_tux.a3");
        System.out.println("gaha:"+url.toString());
        new CarBattleImpl(args);
    }
}
