/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packGame;
import packMap.Hex;
import packMap.Direction;
import packEntities.*;
import packEntities.Buildings.*;
import packEntities.Units.*;
import packEvents.*;

/**
 *
 * @author piotr
 */
public class CompiledClass extends BaseObject {

    /**
     *
     */
    @Override
    public void run() {
        Hex enemyHQCoordinates = null;
        EntityDescriptor enemyHQ = null;
        player.addCommentToLog("Searching for enemy HQ");
        outerloop:
        for(Hex[] i : map)
        {
            for(Hex j : i)
            {
                if(j.getFreeSlots() < j.InitSlots())
                {
                    enemyHQCoordinates = j;
                    for(EntityDescriptor enemyUnit : j.getEntities())
                    {
                        if(enemyUnit.getEntityType().equals(City.class.getSimpleName()))
                        {
                            enemyHQ = enemyUnit;
                            player.addCommentToLog(String.format("Found enemy HQ at %d : %d",j.coordinates.x , j.coordinates.y));
                            break outerloop;
                        }
                    }
                }
            }
        }
        if(enemyHQ == null || enemyHQCoordinates == null)
        {
            player.addCommentToLog("Failed at finding HQ");
            return;
        }
        else player.addCommentToLog("Found enemy HQ");
        
        player.addCommentToLog("Making a harvester");
        Harvester harvester = (Harvester)hq.MakeAndDeploy("Harvester", Direction.S);
        if(harvester == null)
        {
            player.addCommentToLog("Failed at making harvester");
            return;
        }
        else player.addCommentToLog("Made harvester successfully");
        
        boolean castleMade = false;
        Castle castle = null;
        while(enemyHQ.getHp() > 0)
        {
             if(player.getResources() < 100)
             {
                 player.addCommentToLog(String.format("Player has %d resources", player.getResources()));
                 player.addCommentToLog("Harvesting");
                 player.addCommentToLog(String.format("Harvester is on %s hex", harvester.getHex().toString()));
                 harvester.Harvest();
                 harvester.Move(hq.getHex());
                 player.addCommentToLog(String.format("Harvester is on %s hex", harvester.getHex().toString()));
                 if(!harvester.ReturnHarvest())
                 {
                     player.addCommentToLog("Failed to return resources");
                     return;
                 }
                 harvester.Move(Direction.S);
                 player.addCommentToLog(String.format("Harvester is on %s hex", harvester.getHex().toString()));
                 
             }
             else
             {
                 
                 if(!castleMade)
                 {
                    player.addCommentToLog("Making castle");
                    Harvester buildier = (Harvester)hq.MakeAndDeploy("Harvester", Direction.N);
                    castle = (Castle)buildier.Construct("Castle", Direction.N);
                    castleMade = true;
                 }
                 else
                 {
                     if(castle == null)
                     {
                        player.addCommentToLog("Castle empty, exiting");
                        return; 
                     }
                     else
                     {
                         player.addCommentToLog("Making warrior");
                         Warrior warrior = (Warrior) castle.MakeAndDeploy("Warrior", Direction.N);
                         if(warrior == null)
                         {
                             player.addCommentToLog("Failed at making warrior");
                             return;
                         }
                         if(warrior.Attack(enemyHQ))
                         {
                             player.addCommentToLog("Failed at attacking enemy HQ");
                             return;
                         }
                     }
                 }
                 
                 player.addCommentToLog("Attacking");
                 Unit warrior = hq.MakeAndDeploy("Harvester",Direction.N);
                 warrior.Move(Direction.N);
                 warrior.Attack(Direction.N);
             }
             if(player.getLogLenght() > 1000) return;
             
             try{
                 Thread.sleep(5);
             }
             catch(Exception e) {}
        }
        player.addCommentToLog("I'm done");
    }
    
    /**
     * Obsługuje stworzenie, podstawową konfiguracje i automatyzacje zbieracza
     */
    private void HarvesterThread()
    {
        // Stworzenie jednostki typu Harvester
        Harvester harvester = (Harvester)hq.MakeAndDeploy("Harvester", Direction.S);
        
        //Stworzenie zachowania na wypadek, jeżeli ta jednostka zostałaby zaatakowana
        Event attackedListener = (Entity entity) -> {
            //Jeżeli ta jednostka otrzyma obrażenia, wycofaj się do bazy
            BackToBase((Unit)entity);
        };
        //Dodanie stworzonej reakcji do jednostki
        harvester.addAttackedListener(attackedListener);
        Harvest(harvester);
    }
    
    private void BackToBase(Unit unit)
    {
        
    }
    private void Harvest(Harvester harvester)
    {
        
    }
    
}
