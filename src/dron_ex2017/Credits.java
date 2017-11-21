// ==================================================================================
// スタッフクレジットを表示する
// ==================================================================================
package dron_ex2017;

// ==================================================================================
// ライブラリ
// ==================================================================================
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Image;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.lang.Thread;
import java.io.InputStream;



public class Credits extends JPanel implements MouseListener, ActionListener, Runnable {
    /* 変数宣言 */
    private final int LINE = 100;                  // クレジットの行数
    private Main m;                                // 画面遷移に必要
    private String str;                            // 文字列
    GameButton backToMenu;                         // メニューに戻る
    private int x, y;                              // クレジットの座標
    Thread thread;                                 // スレッド
    private String[] crd = new String[LINE];       // クレジット保管用配列
    FileReader f;                                  // ファイル読み込み
    /* ↓絶対パスでないと動かない。各自で調整 スタッフクレジットのパス */
    private String path = "/Users/Matrix5411/Desktop/DronEx/src/dron_ex2017/credits.txt";
    BufferedReader br;                             // テキストを読み込むためのバッファ
    int i = 0;                                     // 行数
    private int back = -500;                       // クレジット戻す
    private int startPos_x = 170;                  // クレジットの初期x座標
    private int startPos_y = 550;                  // クレジットの初期y座標
    private Image image;                   // 画像バッファ
    ImageIcon backMenuButton_n;                    // 通常状態のボタン
    ImageIcon backMenuButton_h;                    // ロールオーバー時のボタン
    ImageIcon backMenuButton_p;                    // 押された状態のボタン
    private final int buttonW = 160;               // メニューに戻るボタンの幅
    private final int buttonH = 35;                // メニューに戻るボタンの高さ
    private ImageIcon backGround;

    // ==================================================================================
    // コンストラクタ
    // ==================================================================================
    public Credits(Main const_main, String s) {
        m = const_main;                                   // 画面遷移に必要
        str = s;                                          // ?

        this.setName("Credits");                             // オブジェクト名を設定これがないと画面遷移できない

        // ==================================================================================
        // スタッフクレジット(文字)
        // ==================================================================================
        /*
        try {
            File file = new File("dron_ex2017/credits.txt");
            String absolutePath = file.getAbsolutePath();
            System.out.println("絶対パス" + absolutePath);
            //System.out.println("既存パス" + path);
            f = new FileReader(absolutePath);  // 絶対パス
            //f = new FileReader(path);
            br = new BufferedReader(f);
            String line;
            while ((line = br.readLine()) != null) {
                crd[i] = line;
                i++;
            }
        } catch (IOException ie) {
            System.out.println("クレジット読み込み不可");
            ie.printStackTrace();
        }
        */
        crd[0] = "◎ 効果音";
        crd[1] = "- ザ・マッチメイカァズ2nd";
        crd[2] = "http://osabisi.sakura.ne.jp/m2/";
        crd[3] = "- 闇詠乃 功良";
        crd[4] = "http://crow.yaekumo.com/ikusa.html";
        crd[5] = "";
        crd[6] = "◎ 担当";
        crd[7] = "- s15t206 : 効果音等";
        crd[8] = "- s15t207 : ゲーム";
        crd[9] = "- s15t212 : UI";
        crd[10] = "- s15t215 : ゲーム";
        crd[11] = "";
        crd[12] = "◎ 参考サイト";
        crd[13] = "- 複数のJPanelで画面遷移2";
        crd[14] = "http://nowloading.blog.jp/archives";
        crd[15] = " /36741210.html";
        crd[16] = "";
        crd[17] = "- ボタンの様々な状態と状態別の画像の設定";
        crd[18] = "https://www.javadrive.jp/tutorial/jbutton";
        crd[19] = " /index10.html";
        crd[20] = "";
        crd[21] = "- WAVEの再生";
        crd[22] = "http://aidiary.hatenablog.com/entry";
        crd[23] = " /20041003/1251379247";
        i = 24;

        x = startPos_x;                                          // クレジットのx位置
        y = startPos_y;                                          // クレジットのy位置

        // ==================================================================================
        // 背景画像の読み込み
        // ==================================================================================
        /*
        try {
            image = ImageIO.read(new File("images/menu_alpha.png"));     // 透明度あり
        } catch (IOException e) {
            System.out.println("画像読み込み不可");
        }
        */
        backGround = new ImageIcon(getClass().getResource("/images/menu_alpha.png"));
        image = backGround.getImage();

        // ==================================================================================
        // ボタン画像の読み込み
        // ==================================================================================
        backMenuButton_n = new ImageIcon(getClass().getResource("/images/backMenuButton/backMenuButton_n.png"));
        backMenuButton_p = new ImageIcon(getClass().getResource("/images/backMenuButton/backMenuButton_p.png"));
        backMenuButton_h = new ImageIcon(getClass().getResource("/images/backMenuButton/backMenuButton_h.png"));
        backToMenu = new GameButton("", backMenuButton_n);
        backToMenu.setPressedIcon(backMenuButton_p);                             // 押された時の状態
        backToMenu.setRolloverIcon(backMenuButton_p);                            // ロールオーバー時の状態
        backToMenu.setContentAreaFilled(false);                                  // デフォルトボタンの中身を消す
        backToMenu.setBorderPainted(false);                                      // デフォルトボタンの枠線を消す
        backToMenu.addActionListener(this);
        backToMenu.setBounds(10, 420, buttonW, buttonH);

        start();                                          // スレッドのスタート
    }


    // ==================================================================================
    // 画面の遷移
    // ==================================================================================
    public void pc(String str) {                             // 画面遷移の要求を出す
        m.PanelChange((JPanel)this, str);                      // 画面遷移の要求
    }

    // ==================================================================================
    // ボタン操作の受け付け
    // ==================================================================================
    public void actionPerformed(ActionEvent event) {
        /* メニューに戻る */
        if (event.getSource() == backToMenu) {
            System.out.println("ボタン押された");  // デバックメッセージ
            pc(m.PanelNames[0]);                 // メニューに戻る
        }
    }

    // ==================================================================================
    // スレッド
    // ==================================================================================
    public void start(){//スタートメソッド
        thread = new Thread(this);                    //スレッドの起動
        thread.start();
    }

    public void stop(){
        thread = null;
    }

    @Override
    public void paintComponent(Graphics g) {
        this.add(backToMenu);                        // メニューに戻るボタンの有効化

        // ==================================================================================
        // 背景画像のスケーリングと表示
        // ==================================================================================
        double panelWidth = getWidth();                      // ウィンドウの幅取得
        double panelHeight = getHeight();                    // ウィンドウの高さ取得
        double imageWidth = image.getWidth(this);            // イメージの幅取得
        double imageHeight = image.getHeight(this);          // イメージの高さ取得
        double sw = panelWidth / imageWidth;                 // 幅をウィンドウにあわせる(sw = scaling width)
        double sh = panelHeight / imageHeight;               // 高さをウィンドウにあわせる(sh = scaling height)
        sw = sw * imageWidth;                                // 画像の幅をスケーリング
        sh = sh * imageHeight;                               // 画像の高さをスケーリング

        super.paintComponent(g);                             // 親クラスのコンストラクタを参照
        g.drawImage(image, 0, 0, (int)sw, (int)sh, this);    // 画像を表示
        g.setFont(new Font("MS UI Gothic", Font.PLAIN, 15)); // フォントその他の指定
        g.setColor(Color.black);                             // 色のセット
        for (int k = 0; k < i; k++) {                        // クレジットの表示
            g.drawString(crd[k] ,x ,y + (20 * k));           // スタッフロールを下にズラしながら一行ずつ表示
        }
    }

    @Override
    public void update(Graphics g){
        paint(g);
    }

    @Override
    public void run(){
        while(true){
            y -= 1;                             //x座標を3ドットずつ減らす
            if (y < back) {
                y = startPos_y;                        // 一定位置まで移動したらy座標を初期に戻す
            }
            repaint();                          //再描画
            try {
                Thread.sleep(10);               //スレッドの待ち合わせ
            } catch (InterruptedException e){   // エラー出力
                e.printStackTrace();
            }
        }

    }

    // ==================================================================================
    // マウスイベント
    // ==================================================================================
    @Override
    public void mouseClicked(MouseEvent ev) {
    }

    @Override
    public void mouseEntered(MouseEvent ev) {
    }

    @Override
    public void mouseExited(MouseEvent ev) {
    }

    @Override
    public void mousePressed(MouseEvent ev) {
    }

    @Override
    public void mouseReleased(MouseEvent ev) {
    }
}
