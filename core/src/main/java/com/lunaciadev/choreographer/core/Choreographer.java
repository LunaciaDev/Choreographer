package com.lunaciadev.choreographer.core;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.utils.Queue;
import com.lunaciadev.choreographer.data.ItemData;
import com.lunaciadev.choreographer.types.Crate;
import com.lunaciadev.choreographer.types.QueueType;
import com.lunaciadev.choreographer.types.Truck;
import com.lunaciadev.choreographer.utils.Signal;

/**
 * The Choreographer is the one who will instruct the dance. Need to be tested
 * throughly.
 * 
 * We divide the incoming crate into 6 sets based on their catagory, sorted by
 * the queueGoal.
 * 
 * Each set is first sorted by crate amount, then sorted by priority. Sort must
 * be stable. It can handle enqueue, dequeue, and undo tasks.
 */
public class Choreographer {
    private class QueueManager {
        private Queue<Crate> lightArmQueue;
        private Queue<Crate> heavyArmQueue;
        private Queue<Crate> heavyAmmoQueue;
        private Queue<Crate> utilitiesQueue;
        private Queue<Crate> medicalQueue;
        private Queue<Crate> uniformsQueue;
        private Queue<Crate> materialsQueue;

        public QueueManager() {
            lightArmQueue = new Queue<>();
            heavyArmQueue = new Queue<>();
            heavyAmmoQueue = new Queue<>();
            utilitiesQueue = new Queue<>();
            medicalQueue = new Queue<>();
            uniformsQueue = new Queue<>();
            materialsQueue = new Queue<>();
        }

        private int processDequeue(Queue<Crate> queue) {
            if (queue.isEmpty()) {
                return -1;
            }

            Crate target = queue.first();

            if (target.queueManufactured()) {
                queue.removeFirst();
            }

            return target.getId();
        }

        public int dequeue(QueueType queueType) {
            switch (queueType) {
                case HEAVY_AMMO:
                    return processDequeue(heavyAmmoQueue);
                case HEAVY_ARMS:
                    return processDequeue(heavyArmQueue);
                case MATERIALS:
                    return processDequeue(materialsQueue);
                case MEDICAL:
                    return processDequeue(medicalQueue);
                case LIGHT_ARMS:
                    return processDequeue(lightArmQueue);
                case UNIFORMS:
                    return processDequeue(uniformsQueue);
                case UTILITIES:
                    return processDequeue(utilitiesQueue);
                default:
                    return -1;
            }
        }

        public void enqueue(QueueType queueType, Crate crate) {
            switch (queueType) {
                case HEAVY_AMMO:
                    heavyAmmoQueue.addFirst(crate);
                    break;
                case HEAVY_ARMS:
                    heavyArmQueue.addFirst(crate);
                    break;
                case LIGHT_ARMS:
                    lightArmQueue.addFirst(crate);
                    break;
                case MATERIALS:
                    materialsQueue.addFirst(crate);
                    break;
                case MEDICAL:
                    medicalQueue.addFirst(crate);
                    break;
                case UNIFORMS:
                    uniformsQueue.addFirst(crate);
                    break;
                case UTILITIES:
                    utilitiesQueue.addFirst(crate);
                    break;
            }
        }

        public void enqueueArray(QueueType queueType, ArrayList<Crate> crateList) {
            Queue<Crate> temp = null;

            switch (queueType) {
                case HEAVY_AMMO:
                    temp = heavyAmmoQueue;
                    break;
                case HEAVY_ARMS:
                    temp = heavyArmQueue;
                    break;
                case LIGHT_ARMS:
                    temp = lightArmQueue;
                    break;
                case MATERIALS:
                    temp = materialsQueue;
                    break;
                case MEDICAL:
                    temp = medicalQueue;
                    break;
                case UNIFORMS:
                    temp = uniformsQueue;
                    break;
                case UTILITIES:
                    temp = utilitiesQueue;
                    break;
            }

            for (Crate crate : crateList) {
                temp.addLast(crate);
            }
        }

        public boolean isFinished() {
            return lightArmQueue.isEmpty() &
                    heavyArmQueue.isEmpty() &
                    heavyAmmoQueue.isEmpty() &
                    utilitiesQueue.isEmpty() &
                    medicalQueue.isEmpty() &
                    uniformsQueue.isEmpty() &
                    materialsQueue.isEmpty();
        }
    }

    private QueueManager queueManager;
    // TODO allow the Trucks to be pooled, reducing allocation rate
    private Queue<Truck> truckQueue;
    private HashMap<Integer, Crate> crateMapping;
    private ItemData itemData;

    /**
     * Emitted after a QueueReqeust.
     * 
     * @param status {@code Boolean} True if the request was handled successfully,
     *               False otherwise.
     * @param truck {@code Truck} The top Truck in the queue.
     */
    public Signal queueRequestComplete;

    /**
     * Emitted after an UndoRequest.
     * 
     * @param status {@code Boolean} True if the request was handled successfully,
     *               False otherwise.
     */
    public Signal undoRequestComplete;

    /**
     * Emitted after a checkFinished, if and only if all queue are empty.
     */
    public Signal reachedManuGoal;

    /**
     * Emitted after a TruckQueue request.
     * 
     * @param truckQueue {@code Queue<Truck>} currently queued Trucks
     */
    public Signal returnTruckQueue;

    /**
     * Emitted after a truck is submitted.
     * 
     * @param queueSize {@code Integer} how many Trucks are queued.
     * @param progress  {@code Float} the progress on finishing the goal.
     * @param truck {@code Truck} The next truck.
     */
    public Signal truckSubmitted;

    public Choreographer(ItemData itemData) {
        queueManager = new QueueManager();
        truckQueue = new Queue<>();
        truckQueue.addFirst(new Truck(itemData));

        queueRequestComplete = new Signal();
        undoRequestComplete = new Signal();
        reachedManuGoal = new Signal();
        returnTruckQueue = new Signal();

        this.itemData = itemData;
    }

    public void setData(InputHandler dataSource) {
        crateMapping = dataSource.getData();

        ArrayList<Crate> lightArmQueue = new ArrayList<>();
        ArrayList<Crate> heavyArmQueue = new ArrayList<>();
        ArrayList<Crate> heavyAmmoQueue = new ArrayList<>();
        ArrayList<Crate> utilitiesQueue = new ArrayList<>();
        ArrayList<Crate> medicalQueue = new ArrayList<>();
        ArrayList<Crate> uniformsQueue = new ArrayList<>();
        ArrayList<Crate> materialsQueue = new ArrayList<>();

        for (Crate crate : crateMapping.values()) {
            switch (itemData.getQueueType(crate.getId())) {
                case HEAVY_AMMO:
                    heavyAmmoQueue.add(crate);
                    break;
                case HEAVY_ARMS:
                    heavyArmQueue.add(crate);
                    break;
                case LIGHT_ARMS:
                    lightArmQueue.add(crate);
                    break;
                case MATERIALS:
                    materialsQueue.add(crate);
                    break;
                case MEDICAL:
                    medicalQueue.add(crate);
                    break;
                case UNIFORMS:
                    uniformsQueue.add(crate);
                    break;
                case UTILITIES:
                    utilitiesQueue.add(crate);
                    break;
            }
        }

        // thanks the star java sort are stable

        lightArmQueue.sort(Crate.compareByQueue);
        lightArmQueue.sort(Crate.compareByPriority);
        heavyArmQueue.sort(Crate.compareByQueue);
        heavyArmQueue.sort(Crate.compareByPriority);
        heavyAmmoQueue.sort(Crate.compareByQueue);
        heavyAmmoQueue.sort(Crate.compareByPriority);
        utilitiesQueue.sort(Crate.compareByQueue);
        utilitiesQueue.sort(Crate.compareByPriority);
        medicalQueue.sort(Crate.compareByQueue);
        medicalQueue.sort(Crate.compareByPriority);
        uniformsQueue.sort(Crate.compareByQueue);
        uniformsQueue.sort(Crate.compareByPriority);
        materialsQueue.sort(Crate.compareByQueue);
        materialsQueue.sort(Crate.compareByPriority);

        queueManager.enqueueArray(QueueType.LIGHT_ARMS, lightArmQueue);
        queueManager.enqueueArray(QueueType.HEAVY_ARMS, heavyArmQueue);
        queueManager.enqueueArray(QueueType.HEAVY_AMMO, heavyAmmoQueue);
        queueManager.enqueueArray(QueueType.UTILITIES, utilitiesQueue);
        queueManager.enqueueArray(QueueType.MEDICAL, medicalQueue);
        queueManager.enqueueArray(QueueType.UNIFORMS, uniformsQueue);
        queueManager.enqueueArray(QueueType.MATERIALS, materialsQueue);
    }

    public int onQueueRequest(QueueType queue) {
        int id = queueManager.dequeue(queue);

        if (id == -1) {
            queueRequestComplete.emit(false, null);
            return -1;
        }

        if (!truckQueue.last().addItem(id)) {
            truckQueue.addLast(new Truck(itemData));
            truckQueue.last().addItem(id);
        }

        queueRequestComplete.emit(true, truckQueue.first());
        return id;
    }

    public boolean onUndoRequest() {
        int id = truckQueue.last().removeLastAdded();

        if (id == -1) {
            undoRequestComplete.emit(false);
            return false;
        }

        if (truckQueue.last().isEmpty() && truckQueue.size != 1) {
            truckQueue.removeLast();
        }

        Crate target = crateMapping.get(id);

        if (target.isCompleted()) {
            queueManager.enqueue(itemData.getQueueType(id), target);
        }
        target.undoQueueManufactured();

        undoRequestComplete.emit(true);
        return true;
    }

    public void onSubmitTruck() {
        truckQueue.removeFirst();

        if (truckQueue.isEmpty()) {
            truckQueue.addFirst(new Truck(itemData));
        }

        truckSubmitted.emit(getQueueSize(), getProgress(), truckQueue.first());
    }

    public boolean onCheckFinished() {
        if (queueManager.isFinished() && (truckQueue.size == 1 && truckQueue.first().isEmpty())) {
            reachedManuGoal.emit((Object) null);
            return true;
        }

        return false;
    }

    public void getTruckQueue() {
        returnTruckQueue.emit(truckQueue);
    }

    public HashMap<Integer, Crate> getResult() {
        // wtf.
        while (onUndoRequest())
            ;
        return crateMapping;
    }

    private int getQueueSize() {
        return truckQueue.size - 1;
    }

    private float getProgress() {
        int crateCompleted = 0;
        int crateNeeded = 0;

        /**
         * Pretty bad implementation here, but as long as the UI dont seize up every
         * submission we should be fine.
         */

        for (Integer key : crateMapping.keySet()) {
            Crate currentCrate = crateMapping.get(key);

            crateCompleted = currentCrate.getQueueMade();
            crateNeeded = currentCrate.getQueueNeeded();
        }

        return crateCompleted / (float) crateNeeded;
    }
}
