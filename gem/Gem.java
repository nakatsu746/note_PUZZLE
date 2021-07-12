package gem;
import java.util.Random;
import java.util.Arrays;

public class Gem{
  private String[] MAX_GEMS = new String[14];
  private final String[] alphas = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N"};
  private final String[] elements = new String[]{ "$", "~", "@", "#", "&", " " };

  private int banishStart;
  private int banishEnd;
  private int continuousNum;
  private String banishGem;

//============================== initial setting ==============================
  public Gem(){
    // 宝石スロットをランダムに作成
    fillGems();
  }

// 宝石スロットをランダムに発生させる処理
  public void fillGems(){
    Random rand = new Random();
    for(int i=0; i<MAX_GEMS.length; i++){
      int num = rand.nextInt(6);
      MAX_GEMS[i] = elements[num];
    }
  }
//============================== gem condition ==============================
// 宝石スロットの表示処理
  public void printGems(){
    // color
    String red    = "\u001b[00;41m";
    String water  = "\u001b[00;46m";
    String green  = "\u001b[00;42m";
    String yellow = "\u001b[00;43m";
    String purple = "\u001b[00;44m";
    String white  = "\u001b[00;47m";
    String end    = "\u001b[00m";
    String[] colors = new String[]{ red, water, green, yellow, purple, white };

    for(int i=0; i<MAX_GEMS.length; i++){
      // エレメントのインデックスを取得
      int num = 0;
      for(int j=0; j<elements.length; j++){
        if(MAX_GEMS[i].equals(elements[j]))
          num = j;
      }
      // エレメントを色付きで表示
      System.out.print(colors[num] + MAX_GEMS[i] + end);
      System.out.print(" ");
    }
    System.out.println();
  }

//============================== command check ==============================
// コマンド入力が正常かの判定処理
  public boolean checkValidCommand(String command){
    System.out.println(command);
    // コマンドが2文字であること
    if(command.length() == 2){
      // コマンドの文字が異なること
      if(command.substring(0,1).equals(command.substring(1,2))){
        System.out.println("同じ文字です。もう一度入力してください");
        return false;
      }
      // コマンドの文字が指定のアルファベットであること
      if(Arrays.asList(alphas).contains(command.substring(0,1))
          && Arrays.asList(alphas).contains(command.substring(1,2))){
        return true;
      }
    }
    System.out.println("コマンドが不正です。もう一度入力してください");
    return false;
  }

//============================== gem moving ==============================
// 宝石を指定位置まで移動させる処理
  public void moveGem(String command){
    // 移動元と移動先の文字を取得
    String startCommand = command.substring(0, 1);
    String endCommand =  command.substring(1, 2);
    // 移動元と移動先のインデックスを取得
    int startNum = 0;
    int endNum = 0;
    for(int i=0; i<alphas.length; i++){
      if(startCommand.equals(alphas[i]))
        startNum = i;
      if(endCommand.equals(alphas[i]))
        endNum = i;
    }
    // 宝石スロットを表示
    printGems();
    // 宝石を入れ替えていく（右側に移動させる場合は、Trueとする）
    boolean swapDirection = true;
    if(startNum < endNum){ // 右方向に入れ替えていく
      for(int i=startNum; i<endNum; i++){
        swapGem(i, swapDirection);
        // 宝石スロットを表示
        printGems();
      }
    } else if(startNum > endNum){ // 左方向に入れ替えていく
      swapDirection = false;
      for(int i=startNum; i>endNum; i--){
        swapGem(i, swapDirection);
        // 宝石スロットを表示
        printGems();
      }
    }
    // 宝石を表示させる
  }

// 指定した方向に隣の宝石を入れ替える処理
  public void swapGem(int num, boolean swapDirection){
    String temp = "";
    temp = MAX_GEMS[num];
    if(swapDirection){
      MAX_GEMS[num] = MAX_GEMS[num+1];
      MAX_GEMS[num+1] = temp;
    } else {
      MAX_GEMS[num] = MAX_GEMS[num-1];
      MAX_GEMS[num-1] = temp;
    }
  }

//============================== banish check ==============================
// 宝石の並びを調べて、消去可能な箇所がないか調べる処理
  public boolean CheckBanishable(){
    banishStart = 0;
    banishEnd = 0;
    continuousNum = 0;
    banishGem = "";
    int startNum = 0;
    int endNum = 0;
    int count=1;
    String continuousGem = "";
    // 宝石が連続で並んでいるか確認
    Outer:
    for(int i=0; i<MAX_GEMS.length-2; i++){
      // 3回連続で宝石が並んでいるかを確認
      if(!MAX_GEMS[i].equals(MAX_GEMS[i+1])
          || !MAX_GEMS[i].equals(MAX_GEMS[i+2]))
        continue;

      // 宝石が3つ以上並んでいることが確定
      continuousGem = MAX_GEMS[i];
      startNum = i;
      endNum = i+2;
      count = 3;
      // 最後から3つ目の場合、連続して並んでいる宝石の数は3つ
      if(i == MAX_GEMS.length-2)
        break Outer;
      for(int j=i+3; j<MAX_GEMS.length; j++){
        if(MAX_GEMS[j].equals(continuousGem)){
          count++;
          endNum = j;
        } else {
          break Outer;
        }
      }
    }
    boolean banishOK = false;
    // 宝石が3つ以上連続で並んでいる場合
    if(count >= 3){
      banishStart = startNum;
      banishEnd = endNum;
      continuousNum = endNum - startNum + 1;
      banishGem = continuousGem;
      banishOK = true;
    }
    return banishOK;
  }

// 消去可能なスロットの宝石を消去する処理
  public void banishGems(){
    // 消去可能な宝石をEMPTYに変換
    for(int i=banishStart; i<=banishEnd; i++){
      MAX_GEMS[i] = elements[elements.length-1];
    }
    printGems();
  }

// 空きスロットの右側に並ぶ宝石を左詰めする処理
  public void shiftGems(){
    // 消した数だけ繰り返す
    for(int i=0; i<continuousNum; i++){
      for(int j=banishStart; j<=MAX_GEMS.length-2; j++){
        // 一つずつ左詰める
        MAX_GEMS[j] = MAX_GEMS[j+1];
      }
      // 最後に無属性のエレメントを入れる
      MAX_GEMS[MAX_GEMS.length-1] = elements[elements.length-1];
      printGems();
    }
  }

// 空きスロットの箇所にランダムな宝石を生成する処理
  public void spawnGems(){
    Random rand = new Random();
    for(int i=MAX_GEMS.length-continuousNum; i<MAX_GEMS.length; i++){
      int num = rand.nextInt(6);
      MAX_GEMS[i] = elements[num];
    }
    printGems();
  }



// Getter
  public String[] getMAX_GEMS(){ return MAX_GEMS; }
  public String getBanishGem(){ return banishGem; }
  public int getContinuousNum(){ return continuousNum; }
}
