package monster;
import element.ElementType;

public class EnemyMonster{
  private String name;
  private int hp;
  private int MAX_HP;
  private String elementType;
  private int attackPower;
  private int defensePower;

// Constract
  public EnemyMonster(String name, int hp, int MAX_HP, String elementType, int attackPower, int defensePower){
    this.name = name;
    this.hp = hp;
    this.MAX_HP = MAX_HP;
    this.elementType = elementType;
    this.attackPower = attackPower;
    this.defensePower = defensePower;
  }

  // color (黒は白とする)
  String red    = "\u001b[00;31m";
  String water  = "\u001b[00;36m";
  String green  = "\u001b[00;32m";
  String yellow = "\u001b[00;33m";
  String purple = "\u001b[00;34m";
  String white  = "\u001b[00;40m";
  String end    = "\u001b[00m";

  String[] colors = new String[]{ red, water, green, yellow, purple, white };
  String[] elements = new String[]{ "$", "~", "@", "#", "&", " " };
// 敵モンスターの名前とエレメントによる色分けを実行
  public String printMonsterName(){
    String nameElement = "";
    for(int i=0; i<colors.length; i++){
      if(elementType == elements[i])
        nameElement = colors[i] + elements[i] + name + elements[i] + end;
    }
    return nameElement;
  }

// Getter
  public String getName(){ return name; }
  public int getHp(){ return hp; }
  public int getMAX_HP(){ return MAX_HP; }
  public String getElementType(){ return elementType; }
  public int getAttackPower(){ return attackPower; }
  public int getDefensePower(){ return defensePower; }

// Setter
  public void setName(String name){ this.name = name; }
  public void setHp(int hp){ this.hp = hp; }
  public void setMAX_HP(int MAX_HP){ this. MAX_HP = MAX_HP; }
  public void setElementType(String elementType){ this.elementType = elementType; }
  public void setAttackPower(int attackPower){ this.attackPower = attackPower; }
  public void setDefensePower(int defensePower){ this.defensePower = defensePower; }
}
