import java.util.Random;

class Character {
    String name;
    int hp;
    int mp;
    Random rand = new Random();

    Character(String name, int hp, int mp){
        this.name = name;
        this.hp = hp;
        this.mp = mp;
    }

    void checkHp(){
        if(hp > 1000) hp = 1000;
        if(hp < 0) hp = 0;
    }

    void checkMp(){
        if(mp > 500) mp = 500;   // ⭐ MP上限
        if(mp < 0) mp = 0;
    }

    void showStatus(){
        System.out.println(name + " HP：" + hp + " MP：" + mp);
    }
  
    //git版本測試用註解
    // ⭐ 普通攻擊（0~200）
    void normalAttack(Character target){
        int damage = rand.nextInt(201); // 0~200
        target.hp -= damage;
        target.checkHp();
        System.out.println(name + " 普通攻擊 " + target.name + "，造成 " + damage + " 傷害！");
    }

    // ⭐ 技能攻擊（200~500）
    void skillAttack(Character target){
        if(mp < 100){
            System.out.println(name + " MP不足，無法使用技能！");
            return;
        }
        int damage = rand.nextInt(301) + 200;
        mp -= 100;
        target.hp -= damage;
        target.checkHp();
        checkMp();
        System.out.println(name + " 使用技能攻擊 " + target.name + "，造成 " + damage + " 傷害！");
    }

    // 被攻擊
    void takeDamage(int damage){
        hp -= damage;
        checkHp();
    }

    // 治療
    void heal(){
        if(mp < 100){
            System.out.println(name + " MP不足，無法治療！");
            return;
        }
        int heal = rand.nextInt(201) + 100;
        mp -= 100;
        hp += heal;
        checkHp();
        checkMp();
        System.out.println(name + " 使用治療，回復 " + heal + " HP！");
    }

    // ⭐ 回復MP（藥水）
    void recoverMp(){
        int recover = rand.nextInt(201) + 100; // 100~300
        mp += recover;
        checkMp(); // ⭐ 不超過500
        System.out.println(name + " 使用藥水，回復 " + recover + " MP！");
    }
}

public class Main {
    static final String VERSION = "1.0.1";

    public static void main(String[] args) {
        System.out.println("=== HaloWord 版本 " + VERSION + " ===");

        Character hero = new Character("勇者", 1000, 500);
        Character monster = new Character("怪物", 1200, 300);

        Random rand = new Random();
        int round = 1;

        System.out.println("=== 戰鬥開始 ===");

        while(hero.hp > 0 && monster.hp > 0){

            System.out.println("\n=== 第 " + round + " 回合 ===");

            // ⭐ 顯示雙方狀態
            hero.showStatus();
            monster.showStatus();

            // 🔴 怪物攻擊
            int monsterDamage = rand.nextInt(201) + 100;
            hero.takeDamage(monsterDamage);
            System.out.println("怪物 攻擊 勇者，造成 " + monsterDamage + " 傷害！");

            if(hero.hp == 0) break;

            // 🟢 勇者行動
            if(hero.hp < 500){
                System.out.println("勇者血量低於500，自動治療！");
                hero.heal();
            } else {
                int choice = rand.nextInt(3); 
                // 0=普通攻擊 1=技能 2=回MP

                if(choice == 0){
                    hero.normalAttack(monster);
                } 
                else if(choice == 1){
                    hero.skillAttack(monster);
                } 
                else {
                    hero.recoverMp();
                }
            }

            // ⭐ 回合結束顯示狀態
            System.out.println(">>> 回合結束狀態：");
            hero.showStatus();
            monster.showStatus();

            round++;
        }

        System.out.println("\n=== 戰鬥結束 ===");

        if(hero.hp == 0){
            System.out.println("勇者死亡...");
        } else {
            System.out.println("怪物被擊敗！");
            System.out.println("勇者獲勝！");
        }
    }
}