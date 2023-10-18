import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javafx.scene.control.Button;

public class Genetic extends BotController{

    public Genetic(Button[][] map){
        this.currentState = map;
    }

    @Override
    public int[] run() {
        int[] bestmove = geneticMove(this.currentState);
        return new int[]{bestmove[0], bestmove[1]};
    }

    public int[] geneticMove(Button[][] map) {
        int populationSize = 28;
        int maxGenerations = 50;
        int tournamentSize = 10;

        // inisialisasi populasi awal   
        List<List<int[]>> population = new ArrayList<>();
        while (population.size() < populationSize) {
            List<int[]> individual = new ArrayList<>();
            while (individual.isEmpty()) {
                int x = new Random().nextInt(8);
                int y = new Random().nextInt(8);
                if (map[x][y].getText().equals("")){
                    int[] coordinates = {x, y};
                    individual.add(coordinates);
                }
            }
            population.add(individual);
        }
        
        // algoritma genetika
        for (int generation = 0; generation < maxGenerations; generation++) {
            // evaluasi fitness setiap individu dalam populasi
            List<Integer> fitnessScores = new ArrayList<>();
            for (List<int[]> individual : population) {
                int fitness = evaluateFitness(individual, map);
                fitnessScores.add(fitness);
            }

            // cek kriteria berhenti
            if (Collections.max(fitnessScores) == 8 * 8 || generation == maxGenerations - 1) {
                List<int[]> bestIndividual = population.get(fitnessScores.indexOf(Collections.max(fitnessScores)));
                return bestIndividual.get(0);
            }

            // seleksi parent menggunakan metode turnamen
            List<List<int[]>> parents = new ArrayList<>();
            for (int i = 0; i < populationSize; i++) {
                List<Integer> tournament = getRandomSample(populationSize, tournamentSize);
                List<Integer> tournamentFitness = new ArrayList<>();
                for (int j : tournament) {
                    tournamentFitness.add(fitnessScores.get(j));
                }
                int winnerIndex = tournament.get(tournamentFitness.indexOf(Collections.max(tournamentFitness)));
                parents.add(population.get(winnerIndex));
            }

            // operasi crossover
            List<List<int[]>> offspring = new ArrayList<>();
            for (int i = 0; i < populationSize - 1; i += 2) {
                int[] parent1 = parents.get(i).get(0);
                int[] parent2 = parents.get(i + 1).get(0);
                int[] child1 = crossover(parent1, parent2);
                int[] child2 = crossover(parent2, parent1);
                
                List<int[]> child1List = new ArrayList<>();
                List<int[]> child2List = new ArrayList<>();
                
                child1List.add(child1);
                child2List.add(child2);
                
                offspring.add(child1List);
                offspring.add(child2List);
            }

            // operasi mutasi
            for (int i = 0; i < offspring.size(); i++) {
                
                int x = offspring.get(i).get(0)[0];
                int y = offspring.get(i).get(0)[1];
                while (!map[x][y].getText().equals("")) {
                    offspring.set(i, mutate(offspring.get(i)));
                    x = offspring.get(i).get(0)[0];
                    y = offspring.get(i).get(0)[1];
                }
                
            }

            // generasi populasi baru
            population = new ArrayList<>(offspring);
        }

        return null;
    }

    private int evaluateFitness(List<int[]> individual, Button[][] map) {
        Button[][] tempBoard = copy(map);

        for (int[] coordinate : individual) {
            int x = coordinate[0];
            int y = coordinate[1];
            tempBoard[x][y].setText("O");
            int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
            for (int[] direction : directions) {
                int dx = direction[0];
                int dy = direction[1];
                int nx = x + dx;
                int ny = y + dy;
                if (nx >= 0 && nx < 8 && ny >= 0 && ny < 8 && tempBoard[nx][ny].getText().equals("X")) {
                    tempBoard[nx][ny].setText("O");
                }
            }
        }

        int xCount = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (tempBoard[i][j].getText().equals("O")) {
                    xCount++;
                }
            }
        }

        return xCount;
    }

    public int[] crossover(int[] parent1, int[] parent2) {
        return new int[] {parent1[0], parent2[1]};
    }

    public List<int[]> mutate(List<int[]> individual) {
        Random random = new Random();
        int mutationPoint = random.nextInt(individual.size());
        int mutationX = random.nextInt(8);
        int mutationY = random.nextInt(8);
        individual.get(mutationPoint)[0] = mutationX;
        individual.get(mutationPoint)[1] = mutationY;
        return individual;
    }
    

    public List<Integer> getRandomSample(int populationSize, int sampleSize) {
        List<Integer> population = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            population.add(i);
        }
        List<Integer> sample = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < sampleSize; i++) {
            int randomIndex = random.nextInt(population.size());
            sample.add(population.remove(randomIndex));
        }
        return sample;
    }


}