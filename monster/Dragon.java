package monster;
import element.ElementType;

public class Dragon extends EnemyMonster{
  public Dragon(){
    super("ドラゴン", 800, 800, ElementType.FIRE.getSymbol(), 50, 40);
  }
}
