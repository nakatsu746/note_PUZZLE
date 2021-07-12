package monster;
import element.ElementType;

public class Bat extends EnemyMonster{
  public Bat(){
    super("オオコウモリ", 300, 300, ElementType.WATER.getSymbol(), 30, 25);
  }
}
