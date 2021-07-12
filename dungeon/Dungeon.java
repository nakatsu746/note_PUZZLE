package dungeon;
import java.util.Random;
import java.util.Scanner;
import party.Party;
import gem.Gem;
import monster.SupportMonster;
import monster.EnemyMonster;

// Constract
public class Dungeon{
  Party party;
  SupportMonster[] supportMonsters;
  EnemyMonster[] enemyMonsters;
  int winCount = 0;

  public Dungeon(Party party, SupportMonster[] supportMonsters, EnemyMonster[] enemyMonsters){
    this.party = party;
    this.supportMonsters = supportMonsters;
    this.enemyMonsters = enemyMonsters;
  }

//============================== battleFlow ==============================
// ダンジョンに到着した時の処理
  public void goDungeon(Party party, SupportMonster[] supportMonsters, EnemyMonster[] enemyMonsters){
    System.out.println(party.getPlayerName() + "はダンジョンに到着した");
    // パーティ編成の表示
    party.showParty(supportMonsters);
    // バトル開始
    for(EnemyMonster enemyMonster : enemyMonsters){
      doBattle(party, supportMonsters, enemyMonster);
    }
  }

// バトル開始の処理
  public void doBattle(Party party, SupportMonster[] supportMonsters, EnemyMonster enemyMonster){
    System.out.println();
    System.out.println("==========================");
    System.out.println(enemyMonster.printMonsterName() + "が現れた！");
    System.out.println("==========================");
    // 宝石スロットのインスタンス化
    Gem gem = new Gem();
    // バトル開始
    while(true){
      // プレイヤーのターン
      if(onPlayerTurn(party, gem, supportMonsters, enemyMonster))
        break;
      // 敵モンスターのターン
      if(onEnemyTurn(party, enemyMonster))
        break;
    }
  }

//============================== player ==============================
// プレイヤーターンの処理
  public boolean onPlayerTurn(Party party, Gem gem, SupportMonster[] supportMonsters, EnemyMonster enemyMonster){
    System.out.println();
    System.out.println("【" + party.getPlayerName() + "のターン】");
    // 現状のバトル状況と宝石スロットを表示
    showBattleField(party, gem, supportMonsters, enemyMonster);
    // 宝石スロットを移動させるコマンドを入力
    boolean commandOK = false;
    String command = "";
    // 入力コマンドの確認
    while(!commandOK){
      System.out.print("コマンド？ > ");
      Scanner sc = new Scanner(System.in);
      command = sc.nextLine();
      // コマンド入力が正常か判定
      commandOK = gem.checkValidCommand(command);
    }
    // 宝石を指定の位置まで移動させる（画面表示）
    gem.moveGem(command);
    // 宝石の並びを調べて、3つ以上連続している場合、処理を行う
    boolean enemyDown = false;
    int combo = 0;
    while(gem.CheckBanishable()){
      combo++;
      // 消去可能な宝石を消去
      gem.banishGems();
      // 回復／攻撃処理
      if(gem.getBanishGem().equals("&")){ // 回復処理へ
        doRecover(party, gem, combo);
      } else if(!gem.getBanishGem().equals(" ")){ // 攻撃処理へ
        enemyDown = doAttack(party, gem, supportMonsters, enemyMonster, combo);
      }
      // 空いた宝石を左に詰める
      gem.shiftGems();
      // 空きスロットに宝石をランダムに生成
      gem.spawnGems();
    }
    return enemyDown;
  }

// プレイヤーの攻撃処理
  public boolean doAttack(Party party, Gem gem, SupportMonster[] supportMonsters, EnemyMonster enemyMonster, int combo){
    // 敵モンスターのHP
    int enemyHp = enemyMonster.getHp();
    // 味方モンスタの攻撃力を計算
    int smAttackPower = calcAttackDamage(gem, supportMonsters, enemyMonster, combo);
    // プレイヤーの攻撃
    System.out.println(enemyMonster.getName() + "に" + smAttackPower + "のダメージ！");
    enemyHp -= smAttackPower;
    // 敵モンスターの生存判定
    if(enemyHp <= 0){ // 敵モンスターを倒した場合
      System.out.println(enemyMonster.getName() + "を倒した！");
      return true;
    }
    enemyMonster.setHp(enemyHp);
    return false;
  }

// 味方モンスターの攻撃ダメージの算出処理
  public int calcAttackDamage(Gem gem, SupportMonster[] supportMonsters, EnemyMonster enemyMonster, int combo){
    int smAttackPower = 0;
    double elementCompatibility = 0;
    for(SupportMonster supportMonster : supportMonsters){
      if(supportMonster.getElementType().equals(gem.getBanishGem())){
        smAttackPower = supportMonster.getAttackPower();
        System.out.println(supportMonster.printMonsterName() + "の攻撃！");
        if(combo > 1)
          System.out.println(combo + " COMBO!");
        elementCompatibility = calcElement(supportMonster.getElementType(), enemyMonster.getElementType());
      }
    }
    double attackPower = (smAttackPower - enemyMonster.getDefensePower()) * Math.pow(1.5, (gem.getContinuousNum() - 3 + (--combo))) * elementCompatibility;
    return (int)(attackPower + blurDamage(attackPower, 10));
  }

// パーティのHP回復処理
  public void doRecover(Party party, Gem gem, int combo){
    if(combo > 1)
      System.out.println(combo + " COMBO!");
    double calcRecover = 20 * (Math.pow(1.5, (gem.getContinuousNum() - 3 + (--combo))));
    int recover = (int)(calcRecover + blurDamage(calcRecover, 10));
    System.out.println(party.getPlayerName() + "は" + recover + "回復した");
    int recoverHp = party.getHp() + recover;

    if(recoverHp > party.getHp()){
      party.setHp(party.getMAX_HP());
    } else {
      party.setHp(recoverHp);
    }
  }

//============================== enemy monster ==============================
// 敵モンスターターンの処理
  public boolean onEnemyTurn(Party party, EnemyMonster enemyMonster){
    System.out.println();
    System.out.println("【" + enemyMonster.getName() + "のターン】");
    // 敵モンスターの攻撃
    return doEnemyAttack(party, enemyMonster);
  }

// 敵モンスターの攻撃処理
  public boolean doEnemyAttack(Party party, EnemyMonster enemyMonster){
    // パーティのHP
    int partyHp = party.getHp();
    // 敵モンスターの攻撃力を計算
    int enemyAttackPower = calcEnemyAttackDamage(party, enemyMonster);
    // 敵モンスターの攻撃
    System.out.println(enemyMonster.printMonsterName() + "の攻撃！");
    System.out.println(enemyAttackPower + "のダメージを受けた！");
    partyHp -= enemyAttackPower;
    // 味方モンスターの生存判定
    if(partyHp <= 0){
      System.out.println(party.getPlayerName() + "は死亡しました");
      return true;
    }
    party.setHp(partyHp);
    return false;
  }

// 敵モンスターによる攻撃ダメージの算出処理
  public int calcEnemyAttackDamage(Party party, EnemyMonster enemyMonster){
    double attackPower = enemyMonster.getAttackPower() - party.getDefensePower();
    return (int)(attackPower + blurDamage(attackPower, 10));
  }

//============================== calc ==============================
// 指定値を中心に指定の幅で数値をランダムにぶれさせる処理
  public int blurDamage(double attackPower, int num){
    double rand = Math.random() * (num+1);
    int randCode = (int)(Math.random() * 2);
    int randNum = 0;
    // 符号と数値を指定の幅でランダムに処理
    if(randCode == 0){
      randNum = (int)(rand * 0.1 * attackPower);
    } else if(randCode == 1){
      randNum = (int)(rand * (-0.1) * attackPower);
    }
    return randNum;
  }

// 属性補正を処理
  public double calcElement(String supportElement, String enemyElement){
    switch(supportElement){
    case "~":
      if(enemyElement.equals("$")){
        return 2.0;
      } else if(enemyElement.equals("#")){
        return 0.5;
      } else {
        return 1.0;
      }
    case "$":
      if(enemyElement.equals("@")){
        return 2.0;
      } else if(enemyElement.equals("~")){
        return 0.5;
      } else {
        return 1.0;
      }
    case "@":
      if(enemyElement.equals("#")){
        return 2.0;
      } else if(enemyElement.equals("$")) {
        return 0.5;
      } else {
        return 1.0;
      }
    case "#":
      if(enemyElement.equals("~")){
        return 2;
      } else if(enemyElement.equals("@")){
        return 0.5;
      } else {
        return 1.0;
      }
    }
    return 0;
  }

//============================== battle condition ==============================
// 現状のバトル状況の表示処理
  public void showBattleField(Party party, Gem gem, SupportMonster[] supportMonsters, EnemyMonster enemyMonster){
    // 敵モンスターの情報を表示
    System.out.println("----------------------");
    System.out.println(enemyMonster.printMonsterName());
    System.out.println(enemyMonster.getHp() + " / " + enemyMonster.getMAX_HP());
    System.out.println();
    // パーティの情報を表示
    for(SupportMonster supportMonster : supportMonsters){
      System.out.print(supportMonster.printMonsterName() + " ");
    }
    System.out.println();
    System.out.println("HP = " + party.getHp() + "/" + party.getMAX_HP());
    // 14個の宝石を表示
    System.out.println("----------------------------");
    String[] alphas = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N"};
    for(int i=0; i<gem.getMAX_GEMS().length; i++){
      System.out.print(alphas[i] + " ");
    }
    System.out.println();
    gem.printGems();
    System.out.println("----------------------------");
  }
}
