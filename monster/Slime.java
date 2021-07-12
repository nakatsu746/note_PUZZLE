package monster;
import element.ElementType;

public class Slime extends EnemyMonster{
  public Slime(){
    super("スライム", 100, 100, ElementType.WATER.getSymbol(), 15, 5);
  }
}
