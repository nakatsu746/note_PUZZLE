package monster;
import element.ElementType;

public class SupportMonster{
  private String name;
  private int hp = 150;
  private int MAX_HP = 150;
  private String elementType;
  private int attackPower;
  private int defensePower;

// Constract
  public SupportMonster(String name, String elementType, int attackPower, int defensePower){
    this.name = name;
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
// 味方モンスターの名前とエレメントによる色分けを実行
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
}
