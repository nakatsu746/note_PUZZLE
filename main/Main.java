package main;

import java.util.Scanner;
// パーティ
import party.Party;
// モンスター
import monster.SupportMonster;
import monster.Seiry;
import monster.Suzaku;
import monster.Byakko;
import monster.Genbu;
import monster.EnemyMonster;
import monster.Slime;
import monster.Goblin;
import monster.Bat;
import monster.Wolf;
import monster.Dragon;
// エレメント
import element.ElementType;
// ダンジョン
import dungeon.Dungeon;

public class Main{
  public static void main(String[] args){
  // プレイヤー名の入力
    System.out.print("プレイヤー名を入力してください。：");
    Scanner sc = new Scanner(System.in);
    String playerName = sc.nextLine();

  // 味方モンスターのインスタンス化
    SupportMonster[] supportMonsters = { new Suzaku(),
       new Seiry(), new Byakko(), new Genbu() };

  // パーティのインスタンス化
    Party party = new Party(playerName, supportMonsters);

  // 敵モンスターのインスタンス化
    EnemyMonster[] enemyMonsters = { new Slime(), new Goblin(),
      new Bat(), new Wolf(), new Dragon() };

  // バトル開始
    Dungeon dungeon = new Dungeon(party, supportMonsters, enemyMonsters);
    dungeon.goDungeon(party, supportMonsters, enemyMonsters);

    System.out.println("*** GAME CLEARED! ***");
  }
}
