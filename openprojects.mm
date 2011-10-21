A3CarSimが利用しているオープンプロジェクト
----------------------------------------

A3CarSimでは様々なオープンプロジェクトの
成果物を活用させていただいています．以下が
その一覧です．


* SuperTuxKart
    + <http://supertuxkart.sourceforge.net/>
* Acerola3D
    + <http://acerola3d.sourceforge.jp/>
* Java3D(+JOALMixer) Ver.1.5.2
    + <https://java3d.dev.java.net/>
    + pache https://java3d.dev.java.net/issues/show_bug.cgi?id=593>
* j3d-vrml97 Ver.06-04-20
    + <https://j3d-vrml97.dev.java.net/>
* JOAL Ver.1.1.2
    + <https://joal.dev.java.net/>
* JOGL Ver.1.1.1
    + <https://jogl.dev.java.net/>
* OpenAL
    + <http://www.openal.org/>
* tritonus plugins Ver 0.3.6
    + <http://www.tritonus.org/plugins.html>
* JLayer - MP3 library  Ver 1.0.1
    + <http://www.javazoom.net/javalayer/javalayer.html>
* JOrbis - Pure Java Ogg Vorbis Decoder Ver 0.0.17
    + <http://www.jcraft.com/jorbis/index.html>
* jbullet - Java port of Bullet Physics Library Release 20101010
    + <http://jbullet.advel.cz/>
* Neverwinter Nights(MDL) Loader Ver 2009,05/14(*)
    + <http://3djava.blogspot.com/2009/05/one-of-most-interesting-model-loader.html>
    + <http://nwn-j3d.sourceforge.net/loader.screenshots.php>
* Quake All Loader (MD2,BSP,MD3) Ver ???(*)
    + <http://www.newdawnsoftware.com/resources/j3d.html>
* AC3D (AC) Loader Ver ???(*)
    + <http://www.newdawnsoftware.com/resources/j3d.html>
* Max3DS(3DS) Loader Ver 1_2(*)
    + <http://java3dsloader.sourceforge.net/>
* Metasequoia (MQO) Loader Ver 2007,07/17(*)
    + <http://kurusugawa.jp/2007/07/17/java3dでmetasequoiaのモデルmqoをロードする/>
* MilkShape3D (MS3D) Loader Ver ???(*)
    + ??? <http://code.google.com/p/compact-car-race/>
* DAELoader(COLLADA) Revision 1.13(2010,10/08)(*)
    + <http://sweethome3d.com/>
    + <http://sweethome3d.cvs.sourceforge.net/viewvc/sweethome3d/SweetHome3D/src/com/eteks/sweethome3d/j3d/DAELoader.java?view=markup>
* XModelImporterJ3D(X3D) Ver 0.8(*)
    + <http://forums.java.net/jive/thread.jspa?threadID=153078>
    + <http://www.interactivemesh.com/downloads/XModelImporterJ3D-0.8.zip>

直接的にはSuperTuxKartの3DモデルとAcerola3Dしか
利用していませんが，他のプロジェクトはAcerola3Dが
利用しているため一覧に入れてありま．
また，上記一覧の中で(*)の印のある物は実際には利用して
おりませんが，Acerola3Dの一部となっているため，
そのまま同梱してあります．

Java3Dに関してはZバッファの読み出しに
関するバグを修正するパッチをあててビルドしなおして
います．

* <https://java3d.dev.java.net/issues/show_bug.cgi?id=593>

またj3d-vrml97は背景画像を読み込むことができるようにする
改良と．WindowsのOctaga Playerと同居できるように修正して
a3-vrml97.jarというファイル名で保存してあります．

NeverWinterNightsのローダ(NWM.jar)は多くのモデルで，指定が
無いにもかかわらずメタリックなマテリアルで表示されてしまう
問題があったのでメタリック表示の機能をとりあえずOFFにして
います．またデフォルトで利用されるテクスチャファイル
(chrome1.tga,W_metal_tex.tga,blur_01.tga)を同梱しています．

