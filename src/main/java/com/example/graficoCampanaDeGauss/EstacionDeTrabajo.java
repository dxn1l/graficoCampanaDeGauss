package com.example.graficoCampanaDeGauss;

import com.example.graficoCampanaDeGauss.Entity.Estudiante;
import com.example.graficoCampanaDeGauss.Repository.EstudianteRepository;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class EstacionDeTrabajo implements Runnable {
    private final BlockingQueue<Estudiante> buffer;
    private final EstudianteRepository estudianteRepository;
    private final Semaphore semaphore;
    private volatile boolean running = true;

    public EstacionDeTrabajo(BlockingQueue<Estudiante> buffer, EstudianteRepository estudianteRepository, Semaphore semaphore) {
        this.buffer = buffer;
        this.estudianteRepository = estudianteRepository;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        while (running) {
            try {
                semaphore.acquire();  // Adquirir un semáforo para controlar el número de hilos que ejecutan simultáneamente
                Estudiante estudiante = new Estudiante(); // Simulación de creación del componente
                // Simular la creación de un componente y colocarlo en el buffer
                buffer.put(estudiante);
                semaphore.release();  // Liberar el semáforo para que otro hilo pueda trabajar
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void stop() {
        this.running = false;
    }
}


