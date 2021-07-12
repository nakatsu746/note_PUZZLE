package party;
import monster.SupportMonster;

public class Party{
  private String playerName;
  private int hp;
  private int MAX_HP;
  private int attackPower;
  private int defensePower;

// Constract
  public Party(String playerName, SupportMonster[] supportMonsters){
    this.playerName = playerName;
    calcPartyParameter(supportMonsters);
  }

// パーティの初期パラメータ設定処理
  public void calcPartyParameter(SupportMonster[] supportMonsters){
    int calchp = 0;
    int calcAttackPower = 0;
    int calcDefensePower = 0;
    // 味方モンスターの合計値を計算
    for(SupportMonster supportMonster : supportMonsters){
      calchp += supportMonster.getHp();
      calcAttackPower += supportMonster.getAttackPower();
      calcDefensePower += supportMonster.getDefensePower();
    }
    // パーティの初期パラメータとして代入
    hp = calchp;
    MAX_HP = calchp;
    attackPower = calcAttackPower;
    defensePower = calcDefensePower / supportMonsters.length;
  }

// 味方モンスターのパラメータ表示（名前・・・HP／攻撃力／防御力）
  public void showParty(SupportMonster[] supportMonsters){
    System.out.println("ーーーーーーーー＜パーティ編成＞ーーーーーーーー");
    for(SupportMonster supportMonster : supportMonsters)
      System.out.println(supportMonster.getName()
          + "・・・HP：" + String.format("%3d", supportMonster.getHp())
          + " ／ 攻撃力：" + String.format("%2d", supportMonster.getAttackPower())
          + " ／ 防御力：" + String.format("%2d", supportMonster.getDefensePower()));
    System.out.println("ーーーーーーーーーーーーーーーーーーーーーーーー");
  }

// Getter
  public String getPlayerName(){ return playerName; }
  public int getHp(){ return hp; }
  public int getMAX_HP(){ return MAX_HP; }
  public int getAttackPower(){ return attackPower; }
  public int getDefensePower(){ return defensePower; }

// Setter
  public void setPlayerName(String playerName){ this.playerName = playerName; }
  public void setHp(int hp){ this.hp = hp; }
}
