import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javafx.scene.control.Button;

public class Genetic extends BotController{

    @Override
    public int[] run() {
        return new int[]{(int) (Math.random()%8), (int) (Math.random()%8)};
    }

    private int evaluateFitness(List<int[]> individual, Button[][] map) {
        Button[][] tempBoard = this.copy(map);

        for (int[] coordinate : individual) {
            int x = coordinate[0];
            int y = coordinate[1];
            if (!tempBoard[x][y].getText().equals("")) {
                continue;
            }
            tempBoard[x][y].setText("X");
            int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
            for (int[] direction : directions) {
                int dx = direction[0];
                int dy = direction[1];
                int nx = x + dx;
                int ny = y + dy;
                if (nx >= 0 && nx < 8 && ny >= 0 && ny < 8 && tempBoard[nx][ny].getText().equals("O")) {
                    tempBoard[nx][ny].setText("X");
                }
            }
        }

        int xCount = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (tempBoard[i][j].getText().equals("X")) {
                    xCount++;
                }
            }
        }

        return xCount;
    }

    public static List<int[]> crossover(List<int[]> parent1, List<int[]> parent2) {
        Random random = new Random();
        int crossoverPoint = random.nextInt(parent1.size() - 1) + 1;
        List<int[]> child = new ArrayList<>();
        child.addAll(parent1.subList(0, crossoverPoint));
        child.addAll(parent2.subList(crossoverPoint, parent2.size()));
        return child;
    }

    public static List<int[]> mutate(List<int[]> individual) {
        Random random = new Random();
        int mutationPoint = random.nextInt(individual.size());
        int mutationX = random.nextInt(8);
        int mutationY = random.nextInt(8);
        individual.get(mutationPoint)[0] = mutationX;
        individual.get(mutationPoint)[1] = mutationY;
        return individual;
    }
    

    public int[] geneticMove(Button[][] map) {
        int populationSize;
        int maxGenerations = 100;
        int tournamentSize = 5;
        double mutationRate = 0.1;

        int emptyCells = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (map[i][j].getText().equals("")) {
                    emptyCells++;
                }
            }
        }

        populationSize = emptyCells;

        // inisialisasi populasi awal
        List<List<int[]>> population = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            List<int[]> individual = new ArrayList<>();
            for (int j = 0; j < 8; j++) {
                int x = new Random().nextInt(8);
                int y = new Random().nextInt(8);
                int[] coordinates = {x, y};
                individual.add(coordinates);
                
            }
            population.add(individual);
        }
        System.out.println("jumlah populasi:" + population.size());


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
            for (int i = 0; i < populationSize-1; i += 2) {
                List<int[]> parent1 = parents.get(i);
                List<int[]> parent2 = parents.get(i + 1);
                List<int[]> child1 = crossover(parent1, parent2);
                List<int[]> child2 = crossover(parent2, parent1);
                offspring.add(child1);
                offspring.add(child2);
            }

            // operasi mutasi
            for (int i = 0; i < offspring.size(); i++) {
                if (Math.random() < mutationRate) {
                    offspring.set(i, mutate(offspring.get(i)));
                }
            }

            // generasi populasi baru
            population = new ArrayList<>(offspring);
        }

        return null;
    }

    public static List<Integer> getRandomSample(int populationSize, int sampleSize) {
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