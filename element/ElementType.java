package element;

public enum ElementType{
  FIRE("$"), WATER("~"), WIND("@"), EARTH("#"), LIFE("&"), EMPTY(" ");

  private final String text;

  private ElementType(final String text){
    this.text = text;
  }
  public String getSymbol(){ return this.text; }
}
