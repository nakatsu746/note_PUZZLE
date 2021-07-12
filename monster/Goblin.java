package monster;
import element.ElementType;

public class Goblin extends EnemyMonster{
  public Goblin(){
    super("ゴブリン", 200, 200, ElementType.EARTH.getSymbol(), 20, 15);
  }
}
