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
    public void testEat(){
        Dinosaure dinosaure = new Dinosaure();
        dinosaure.setFood(100);
        dinosaure.setHappiness(500);
        dinosaure.setWallet(999);
        dinosaure.buyApple();
        dinosaure.buyGoldenApple();

        dinosaure.eat("Apple");
        assertEquals(350.0,dinosaure.getFood(),0.001);
        dinosaure.eat("GoldenApple");
        assertEquals(750.0,dinosaure.getFood(),0.001);
        assertEquals(575.0,dinosaure.getHappiness(),0.001);

        dinosaure.eat("GoldenApple");
        dinosaure.eat("Apple");
        assertEquals(750.0,dinosaure.getFood(),0.001);
        assertEquals(575.0,dinosaure.getHappiness(),0.001);
    }

    //_____________ACTION__________

    @Test
    public void testWork(){
        Robot robot = new Robot(1, "NomRobot", 1);
        robot.getDifficulty();
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

    @Test
    public void testJouer() {
        float origine_happiness = 150;


        Animal animal = new Chat(1, "TestAnimal", 1);
        animal.setHappiness(origine_happiness);
        animal.play();

        assertTrue(800 < animal.getHygiene() && animal.getHygiene() <= 925);

        // Vérifier le comportement attendu en fonction de la difficulté
        if (origine_happiness < 100) {
            // Si la joie est inférieure à 100, elle devrait être ajustée selon la formule
            assertEquals(750 - 2 * 100, animal.getHappiness(), 0.1);
        } else if (origine_happiness <= 200) {
            // Si la joie est entre 100 et 200, elle devrait être ajustée selon la formule
            assertEquals(900 - animal.getDifficulty() * 100, animal.getHappiness(), 0.1);
        } else {
            // Si la joie est supérieure à 200, elle devrait être ajustée au maximum (1000)
            assertEquals(1000, animal.getHappiness(), 0.1);

            Robot robot = new Robot(1, "TestRobot", 1);


            robot.setHappiness(origine_happiness);
            robot.jouer();


            // Vérifier le comportement attendu en fonction de la difficulté
            if (origine_happiness < 100) {
                // Si la joie est inférieure à 100, elle devrait être ajustée selon la formule
                assertEquals(750 - 2 * 100, robot.getHappiness(), 0.1);
            } else if (origine_happiness <= 200) {
                // Si la joie est entre 100 et 200, elle devrait être ajustée selon la formule
                assertEquals(900 - robot.getDifficulty() * 100, robot.getHappiness(), 0.1);
            } else {
                // Si la joie est supérieure à 200, elle devrait être ajustée au maximum (1000)
                assertEquals(1000, robot.getHappiness(), 0.1);
            }


            assertTrue(800 < robot.getSoftware() & robot.getSoftware() <= 925);
        }

    }

    @Test
    public void testUpdating() {
        // Créer une instance de Robot avec une difficulté spécifique pour le test
        Robot robot = new Robot(2, "NomRobot", 1);

        // Définir le bonheur et le logiciel à des valeurs spécifiques pour le test
        float origine_happiness = 900;
        float origine_software = 150;
        robot.setHappiness(origine_happiness);
        robot.setSoftware(origine_software);

        // Appeler la méthode updating
        robot.updating();

        // Vérifier le comportement attendu pour le bonheur
        assertEquals(1000, robot.getHappiness(), 0.1);

        // Vérifier le comportement attendu pour le logiciel
        if (origine_software <= 200) {
            // Si le logiciel est inférieur à 200, il devrait être ajusté selon la formule
            assertEquals(900 - robot.getDifficulty() * 85, robot.getSoftware(), 0.1);
        } else {
            // Si le logiciel est supérieur à 200, il devrait être ajusté au maximum (1000)
            assertEquals(1000, robot.getSoftware(), 0.1);
        }
    }

    @Test
    public void testWash() {
        Animal animal = new Dinosaure(1, "TestAnimal", 1);
        float hygiene_origine = 100;

        animal.setHygiene(hygiene_origine);  // Mettez une valeur initiale pour tester

        // Effectuer l'action de se laver
        animal.wash();

        // Vérifier que l'hygiène est correctement ajustée
        if (hygiene_origine <= 200) {
            assertEquals(900 - animal.getDifficulty() * 85, animal.getHygiene(), 0.1);
        } else {
            assertEquals(1000, animal.getHygiene(), 0.1);
        }
    }
}
