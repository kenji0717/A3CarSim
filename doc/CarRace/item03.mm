title: 2012年度CarRace環境の公開

2012年度CarRace環境の公開
========================================

(2012,08/22)2012年度の北海道情報大学のプログラミング
コンテストで使用する，CarRace環境を公開します．

本年度のCarRace環境
----------------------------------------

大変遅くなり申し分けないです．昨年に引き続きプログラミング
コンテストでCarRace部門のプログラムを募集します．基本的に
昨年度と同様，車の制御プログラムを書いてもらってコースを
一周するタイムを競います．コースも同じで，開発環境の使用方法も
変更ありませんが，利用できるAPIが追加され，より簡単に制御
プログラムを作成することができるようになりました．是非，
たくさんの人に参加してもらいたいと思います．

変更の趣旨
----------------------------------------

昨年度実施されたCarRace部門の環境では，APIを使って得ることが
できる情報は，自分の車とシミュレーション時間だけでした．そのため，
コースのどこを走らなければならないかは，公開されたデータから
座標を読み取ったり，プログラムを解析する必要がありました．
そのため，車の制御プログラムの本質以外の部分で苦労した人も
多いと思います．

今年のAPIでは，コースのセンターラインに関する情報が取得でき
るようになりました．自分の車から一番近いセンターライン上の
1点を座標ベクトルとして受けとることができます．また，その点に
おけるコースの進行方向も，方向ベクトルとして受けとれます．
さらに，自分の車から一番近いセンターライン上の点だけでなく，
その点より前方のコース上の点の情報も引き出せます．例えば
コース上の10メートル前方の点を目標として走行させたり，
10メートル前方のコースの方向をチェックすることで，
カーブにさしかかる前にカーブを検知できます．

その他，getDistメソッドでスタート位置からのおおよその走行距離が
得られるので，今自分がコースのどの部分を走っているのかの判定が
簡単に行えます．さらに自分の車の速度ベクトルを得るためのAPIも
追加されているので，エンジン出力の調整で速度を制御するのが楽に
行えます．

また，新しく追加したAPI以外の部分は昨年度と変更ありません．
コースも車の性能も昨年度とまったく同じです．

追加されたAPIについて
----------------------------------------

APIの追加はRaceCarBaseクラスのメソッドとして追加されています．

* <http://kenji0717.github.com/A3CarSim/doc/api/com/github/kenji0717/a3cs/RaceCarBase.html>

上記ページの「メソッド詳細」の項目でgetVelメソッド以下が，本年度
新しく追加されたAPIです．詳細はこのAPIのページを読んで欲しいのですが，
以下に簡単に紹介します．

* getVelメソッド
    + 車の速度を方向ベクトルとして取得できるので，
      速度の制御がしやすくなるでしょう．
* getDistメソッド
    + スタート地点から現在地点に最も近いセンター
      ライン上の一点まで，コースのセンターラインに沿って
      走行した時の走行距離を取得できます．
* getPointメソッド
    + 引数無しと，実数の引数を取るメソッドがあります．
      引数無しの場合は，センタライン上で車に最も近い
      点を計算して返してくれます．車の座標と比較すれば
      どれだけセンターからずれているかわかるでしょう．
      引数ありの場合は，センターラインの一番近い場所から
      引数メートル前方のセンターライン上の点を計算して
      返してくれます．どちらに向って走れば良いのかの
      指標になります．
* getDirectionメソッド
    + getPointメソッド同様，引数無しと有りがあります．
      getPointで得られる一点におけるセンターラインの
      前方方向を，単位ベクトルとして返してくれます．
* getRawPointDataメソッド
    + コースの3Dデータから抽出したセンターライン上の
      点の情報を引き出すことができます．一周690ポイントの
      データが保存されており，getDist,getPoint,getDirectionの
      各メソッドでは，この生情報から線形補完した値を返す
      ようになっています．

サンプルプログラムについて
----------------------------------------

新しいAPIを使用するCarRaceプログラムのサンプルを公開します．

* <http://kenji0717.github.com/A3CarSim/doc/CarRace/SampleProgram.html>

昨年度のサンプルプログラムよりも短いですが，昨年度の優勝プログラム
よりも速いタイムを叩き出します．なかなかの走りをするので，是非実行
してみて下さい．パラメータを少し変更して実行してみると面白いでしょう．

それからちょっとお願いですが，上記サンプルプログラムの走行タイムは
44.97秒となるはずですが，異なる走行タイムが測定されることがあれば
連絡下さい．

どのように工夫するかのヒント
----------------------------------------

サンプルプログラムのパラメータを変更するだけでも，まだ多少
改善することは可能ですが，是非，独自のアプローチで制御プログラムを
再構成してくれると良いと思います．

* getDistメソッドを用いて走行している場所を特定して，
  走っている場所によって，走行モードを切り替えて制御する．
* サンプルプログラムでは，坂道判定や急カーブ判定をbooleanの
  true,falseの2値で行っていますが，連続的にコントロール
  してみる．
* [PID制御](http://ja.wikipedia.org/wiki/PID制御)などの
  制御理論を少し勉強してスピードや方向の制御をしてみる．
* getRawPointメソッドからセンターラインの情報を引き出し，
  何らかの方法で，より良い走行ラインを計算してから，その
  ラインにそって走らせる．

その他情報源
----------------------------------------

新しいAPIが追加されていますが，基本的に昨年度と同じシミュレーション・
開発環境ですので，使用方法・開発方法は昨年度の資料も参考にして下さい．

* <http://kenji0717.github.com/A3CarSim/doc/CarRace/youtube.html>

その他，連絡先など
----------------------------------------

昨年度は，一部の環境でJava Web Startによるインストールが上手く
動作しないということで急遽ダウンロード版も公開したりしていました．
心当たりのある部分を修正したりしていますが，あらゆる環境でテスト
ているわけではないので，みなさんに実行してもらい改善してゆきたいと
思っています．今年度の環境のダウンロード版は作成していませんが，
必要であれば作成するので連絡をして下さい．

その他カーレース部門における質問などあれば，
プログラミングコンテスト実行委員<procon@do-johodai.ac.jp>
に連絡下さい．
