package org.sim;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.sim.fish.Fish;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SimDataMonitor {
    private final List<List<String>> data = new ArrayList<>();

    public void BuildHeader()
    {
        data.add(List.of("Ticks", "Fish population", "Average Speed", "Average MaxHP",
                        "Average Damage", "Average Vision Range",
                        "Average Aggressiveness", "Average PlantToMeatDigestion"));
    }

    public void GatherData()
    {
        if(SimManager.FishCount == 0) return;

        float sumSpeed = 0;
        float sumMaxHP = 0;
        float sumDamage = 0;
        float sumVisionRange = 0;
        float sumAggressiveness = 0;
        float sumPlantToMeatDigestion = 0;

        for (Entity e : SimManager.Entities) {
            if(e instanceof Fish)
            {
                Fish f = ((Fish) e);
                sumSpeed += f.Attributes.Speed;
                sumMaxHP += f.Attributes.MaxHP;
                sumDamage += f.Attributes.Damage;
                sumVisionRange += f.Attributes.VisionRange;
                sumAggressiveness += f.Attributes.Aggressiveness;
                sumPlantToMeatDigestion += f.Attributes.PlantToMeatDigestion;
            }
        }

        data.add(List.of(
            String.format("%d", SimManager.Ticks),
            String.valueOf(SimManager.FishCount),
            String.format("%.2f", SimManager.FishCount > 0 ? sumSpeed / SimManager.FishCount : 0f),
            String.format("%.2f", SimManager.FishCount > 0 ? sumMaxHP / SimManager.FishCount : 0f),
            String.format("%.2f", SimManager.FishCount > 0 ? sumDamage / SimManager.FishCount : 0f),
            String.format("%.2f", SimManager.FishCount > 0 ? sumVisionRange / SimManager.FishCount : 0f),
            String.format("%.2f", SimManager.FishCount > 0 ? sumAggressiveness / SimManager.FishCount : 0f),
            String.format("%.2f", SimManager.FishCount > 0 ? sumPlantToMeatDigestion / SimManager.FishCount : 0f)
        ));

    }

    public void ExportCsv(String fileName)
    {

        try (FileWriter fileWriter = new FileWriter(fileName, false);
             CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT)) {
            for (List<String> row : data) {
                csvPrinter.printRecord(row);
            }
            csvPrinter.flush();
        } catch (IOException e) {
            throw new RuntimeException("Failed to export simulation data to CSV", e);
        }
    }

}
