package com.github.kenji0717.a3cs;

/**
 * CarRaceで使用される車の性能をキーボード操作で感覚的に
 * 確認するためのプログラムです。mainメソッドしかありません。
 * 上下のキーで前に加速、後ろに加速、左右のキーでステアリング操作、
 * スペースキーでブレーキ、シフトキーで強制ドリフト走行の操作が
 * できます。
 */
public class RaceTest {
    /**
     * CarRaceで使用される車の性能をキーボード操作で感覚的に
     * 確認するためのプログラムを起動します。
     */
    public static void main(String args[]) {
        new RaceTestImpl();
    }
}
