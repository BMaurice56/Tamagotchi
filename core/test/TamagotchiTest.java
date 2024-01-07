import com.mygdx.game.Personnage.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class TamagotchiTest {

    @Test
    public void testDinosaure() {
        Dinosaure dino = new Dinosaure();
        dino.setWallet(100);
        dino.setFood(700);
        dino.setHappiness(650);
        dino.setSleep(201);

        assertEquals(100, dino.getWallet(), 0.001);
        assertEquals(700.0, dino.getFood(), 0.001);
        assertEquals(650.0, dino.getHappiness(),0.001);
        assertEquals(201.0, dino.getSleep(), 0.001); // Ajoutez une tolérance de 0 pour les nombres entier

    }

    @Test
    public void testRobot() {

        Robot robot = new Robot();

        robot.setHappiness(666);
        robot.setTank(200);
        robot.setBattery(350);
        robot.setDurability(800);
        robot.setWallet(100);

        assertEquals(666.0, robot.getHappiness(),0.001);
        assertEquals(200.0, robot.getTank(),0.001);
        assertEquals(350.0,robot.getBattery(),0.001);
        assertEquals(800.0, robot.getDurability(),0.001);
        assertEquals(100.0, robot.getWallet(),0.001);

        // Vérifier que les valeurs sont ajustées aux limites inférieures
        robot.setHappiness(-100);
        assertEquals(0, robot.getHappiness(), 0.001);

        robot.setTank(-100);
        assertEquals(0, robot.getTank(), 0.001);

        robot.setBattery(-100);
        assertEquals(0, robot.getBattery(), 0.001);

        robot.setDurability(-100);
        assertEquals(0, robot.getDurability(), 0.001);

        robot.setWallet(-100);
        assertEquals(0, robot.getWallet(), 0.001);

        // Vérifier que les valeurs sont ajustées aux limites supérieures
        robot.setHappiness(1500);
        assertEquals(1000, robot.getHappiness(), 0.001);

        robot.setTank(1200);
        assertEquals(1000, robot.getTank(), 0.001);

        robot.setBattery(1200);
        assertEquals(1000, robot.getBattery(), 0.001);

        robot.setDurability(1200);
        assertEquals(1000, robot.getDurability(), 0.001);

        robot.setWallet(1200);
        assertEquals(1000, robot.getWallet(), 0.001);

    }


    //_____________FOOD_________________
    @Test
    public void testBuyApple() {
        Chien chien = new Chien();
        chien.setWallet(130);

        chien.buyApple();
        chien.buyApple();
        chien.buyApple();
        assertEquals(70,chien.getWallet());

        chien.buyGoldenApple();
        assertEquals(10,chien.getWallet());

        //plus assez de sous pour achetr donc normalement son porte monnaie ne bouge pas
        chien.buyApple();
        chien.buyGoldenApple();
        assertEquals(10,chien.getWallet());
    }

    @Test
    public void testNumberAplle(){
        Chat chat = new Chat();
        chat.setFood(100);
        chat.addBasket(new Apple());
        chat.addBasket(new GoldenApple());
        chat.addBasket(new GoldenApple());
        chat.addBasket(new Apple());
        chat.addBasket(new Apple());

        assertEquals(3,chat.getNumberOfFood("Apple"));
        assertEquals(2,chat.getNumberOfFood("GoldenApple"));

        chat.eat("GoldenApple");
        assertEquals(1,chat.getNumberOfFood("GoldenApple"));
        //on conssome toute les pommes
        chat.eat("Apple");
        chat.eat("Apple");
        chat.eat("Apple");
        assertEquals(0,chat.getNumberOfFood("Apple"));
        //on tente d'en conssomer alors qu'il y en a plus
        chat.eat("Apple");
        assertEquals(0,chat.getNumberOfFood("Apple"));

        chat.eat("GoldenApple");
        chat.eat("GoldenApple");
        assertEquals(0,chat.getNumberOfFood("GoldenApple"));
    }

    @Test
    public void testNumberOil() {
        Robot robot = new Robot();
        robot.setTank(1000);
        robot.addBasket(new Oil());
        robot.addBasket(new SuperOil());
        robot.addBasket(new SuperOil());
        robot.addBasket(new Oil());
        robot.addBasket(new Oil());

        assertEquals(3, robot.getNumberOil());
        assertEquals(2, robot.getNumberSuperOil());

        robot.fillTank("SuperOil");
        assertEquals(1, robot.getNumberSuperOil());

        // on consomme tout l'huile
        robot.fillTank("Oil");
        robot.fillTank("Oil");
        robot.fillTank("Oil");
        assertEquals(0, robot.getNumberOil());

        // on tente d'en consommer alors qu'il n'y en a plus
        robot.fillTank("Oil");
        assertEquals(0, robot.getNumberOil());

        robot.fillTank("SuperOil");
        robot.fillTank("SuperOil");
        assertEquals(0, robot.getNumberSuperOil());
    }

    @Test
    public void testAnimalEat(){

        // initialisation dino 100 food 500 hapiness qui possede une Apple et une GoldenApple
        Dinosaure dinosaure = new Dinosaure();
        dinosaure.setFood(100);
        dinosaure.setHappiness(500);
        dinosaure.setWallet(999);
        dinosaure.buyApple();
        dinosaure.buyGoldenApple();

        //mange toutes ses pommes test resultat
        dinosaure.eat("Apple");
        assertEquals(350.0,dinosaure.getFood(),0.001);
        dinosaure.eat("GoldenApple");
        assertEquals(750.0,dinosaure.getFood(),0.001);
        assertEquals(575.0,dinosaure.getHappiness(),0.001);

        //test de manger alors qu'il n'a plus de pommes test objectif resultat inchangé
        dinosaure.eat("GoldenApple");
        dinosaure.eat("Apple");
        assertEquals(750.0,dinosaure.getFood(),0.001);
        assertEquals(575.0,dinosaure.getHappiness(),0.001);

        //possede une nouvelle pomme et fodd à 1000 objectif ne pas depasser les 1000 et pomme consommé
        dinosaure.buyGoldenApple();
        dinosaure.setFood(1000);
        dinosaure.eat("GoldenApple");
        assertEquals(1000.0,dinosaure.getFood(),0.01);
        assertEquals(650.0,dinosaure.getHappiness(),0.001);
        assertEquals(0,dinosaure.getNumberGoldenApple());

    }

    @Test
    public void testRobotEating() {
        Robot robot = new Robot();
        robot.setTank(100);
        robot.setHappiness(500);
        robot.setWallet(999);

        robot.buyOil();
        robot.buySuperOil();

        robot.fillTank("Oil"); //test resultat conso huile
        assertEquals(350.0, robot.getTank(), 0.001);

        robot.fillTank("SuperOil");  //test resultat conso SuperHuile
        assertEquals(750.0, robot.getTank(), 0.001);
        assertEquals(575.0, robot.getHappiness(), 0.001);


        robot.fillTank("SuperOil"); //test manger sans avoir d'huile dans le basket
        assertEquals(750.0, robot.getTank(), 0.001);
        assertEquals(575.0, robot.getHappiness(), 0.001);


    }


    //_____________ACTION__________

    @Test
    public void testWork(){
        Robot robot = new Robot(1, "NomRobot", 1);

        // Obtenez les valeurs initiales
        float initialHappiness = robot.getHappiness();
        float initialSoftware = robot.getSoftware();
        float initialDurability = robot.getDurability();
        float initialWallet = robot.getWallet();

        // Appelez la méthode work
        robot.work();

        // Obtenez les nouvelles valeurs
        float newHappiness = robot.getHappiness();
        float newSoftware = robot.getSoftware();
        float newDurability = robot.getDurability();
        float newWallet = robot.getWallet();

        // Vérifiez que les valeurs ont été ajustées correctement en fonction de la difficulté
        assertEquals(initialHappiness - robot.getDifficulty() * 100, newHappiness, 0.01);
        assertEquals(initialSoftware - robot.getDifficulty() * 43, newSoftware, 0.01);
        assertEquals(initialDurability - robot.getDifficulty() * 60, newDurability, 0.01);

        // Vérifiez que le porte-monnaie n'a pas changé (car work n'ajuste pas le porte-monnaie)
        assertEquals(initialWallet+50, newWallet,0.001);
    }

    @Test
    public void testSleepMaintenace() {

        Animal animal = new Chat(1, "TestAnimal", 1);
        animal.setSleep(201);

        animal.sleep();

        // Vérifiez si la valeur de sommeil a été ajustée à son maximum (1000)
        assertEquals(1000.0, animal.getSleep(), 0.001);

        animal.setSleep(200);
        animal.sleep();
        double expectedSleep = 900.0 - animal.getDifficulty() * 100.0;
        assertEquals(expectedSleep, animal.getSleep(), 0.001);

        // LA meme mais pour le robot

        Robot robot = new Robot();
        robot.setDurability(201);

        robot.maintenance();

        assertEquals(1000.0, robot.getDurability(), 0.001);

        robot.setDurability(200);
        robot.maintenance();
        double expectedDurability = 900.0 - animal.getDifficulty() * 100.0;
        assertEquals(expectedDurability, robot.getDurability(), 0.001);
    }


    public void verifyHappinessA(float origineHappiness,Animal animal) {

        animal.setHappiness(origineHappiness);
        animal.play();

        assertTrue(800 < animal.getHygiene() && animal.getHygiene() <= 925);

        // Vérifier le comportement attendu en fonction de la difficulté
        if (origineHappiness < 100) {
            // Si la joie est inférieure à 100, elle devrait être ajustée selon la formule
            assertEquals(750 - animal.getDifficulty() * 100, animal.getHappiness(), 0.1);
        } else if (origineHappiness <= 200) {
            // Si la joie est entre 100 et 200, elle devrait être ajustée selon la formule
            assertEquals(900 - animal.getDifficulty() * 100, animal.getHappiness(), 0.1);
        } else {
            // Si la joie est supérieure à 200, elle devrait être ajustée au maximum (1000)
            assertEquals(1000, animal.getHappiness(), 0.1);
        }
    }
    public void verifyHappinessR(float origineHappiness,Robot robot) {

        robot.setHappiness(origineHappiness);
        robot.jouer();

        assertTrue(800 < robot.getSoftware() && robot.getSoftware() <= 925);

        // Vérifier le comportement attendu en fonction de la difficulté
        if (origineHappiness < 100) {
            // Si la joie est inférieure à 100, elle devrait être ajustée selon la formule
            assertEquals(750 - robot.getDifficulty() * 100, robot.getHappiness(), 0.1);
        } else if (origineHappiness <= 200) {
            // Si la joie est entre 100 et 200, elle devrait être ajustée selon la formule
            assertEquals(900 - robot.getDifficulty() * 100, robot.getHappiness(), 0.1);
        } else {
            // Si la joie est supérieure à 200, elle devrait être ajustée au maximum (1000)
            assertEquals(1000, robot.getHappiness(), 0.1);
        }
    }

    @Test
    public void testJouer() {


        verifyHappinessA(99,new Chat(1, "TestAnimal", 1));
        verifyHappinessA(170,new Chat(1, "TestAnimal", 1));
        verifyHappinessA(400,new Chat(1, "TestAnimal", 1));

        verifyHappinessR(99,new Robot(1,"test",1));
        verifyHappinessR(199,new Robot(1,"test",1));
        verifyHappinessR(299,new Robot(1,"test",1));
        }


    public void verifySoftware(float origineSoftware, Robot robot) {
        // Vérifier le comportement attendu en fonction de la difficulté
        if (origineSoftware <= 200) {
            // Si le logiciel est inférieur à 200, il devrait être ajusté selon la formule
            assertEquals(900 - robot.getDifficulty() * 85, robot.getSoftware(), 0.1);
        } else {
            // Si le logiciel est supérieur à 200, il devrait être ajusté au maximum (1000)
            assertEquals(1000, robot.getSoftware(), 0.1);
        }
    }

    @Test
    public void testUpdating() {

        // Créer une instance de Robot avec une difficulté spécifique pour le test
        Robot robot = new Robot(1, "NomRobot", 1);

        // Définir le bonheur et le logiciel à des valeurs spécifiques pour le test
        float origine_happiness = 900;
        float origine_software = 150;
        robot.setHappiness(origine_happiness);
        robot.setSoftware(origine_software);

        // Appeler la méthode updating
        robot.updating();

        // Vérifier le comportement attendu pour le bonheur
        assertEquals(1000, robot.getHappiness(), 0.1);

        // Appeler la fonction de vérification du logiciel
        verifySoftware(origine_software, robot);




        // Créer une instance de Robot avec une difficulté spécifique pour le test
        Robot robot2 = new Robot(1, "NomRobot", 1);

        // Définir le bonheur et le logiciel à des valeurs spécifiques pour le test
        float origine_happiness2 = 900;
        float origine_software2 = 150;
        robot2.setHappiness(origine_happiness2);
        robot2.setSoftware(origine_software2);

        // Appeler la méthode updating
        robot2.updating();

        // Vérifier le comportement attendu pour le bonheur
        assertEquals(1000, robot2.getHappiness(), 0.1);

        // Appeler la fonction de vérification du logiciel
        verifySoftware(origine_software2, robot2);


    }

    /*
     * Vérifie le comportement de la méthode wash() sur l'hygiène d'un animal en fonction de la difficulté.
     * @param origineHygiene La valeur initiale de l'hygiène avant l'action de se laver.
     * @param animal L'instance de Animal sur laquelle l'action de se laver est effectuée.
     */
    public void verifyHygiene(float origineHygiene, Animal animal) {
        animal.setHygiene(origineHygiene);  // Mettez une valeur initiale pour tester
        animal.wash();
        // Vérifier le comportement attendu en fonction de la difficulté
        if (origineHygiene <= 200) {
            // Si l'hygiène est inférieure à 200, elle devrait être ajustée selon la formule
            assertEquals(900 - animal.getDifficulty() * 85, animal.getHygiene(), 0.1);
        } else {
            // Si l'hygiène est supérieure à 200, elle devrait être ajustée au maximum (1000)
            assertEquals(1000, animal.getHygiene(), 0.1);
        }
    }
    @Test
    public void testWash() {

        verifyHygiene(150,new Chat(1,"Test",1));
        verifyHygiene(250,new Chat(1,"Test",1));
    }
}
