package monster;
import element.ElementType;

public class Wolf extends EnemyMonster{
  public Wolf(){
    super("ウェアウルフ", 400, 400, ElementType.WIND.getSymbol(), 40, 30);
  }
}
