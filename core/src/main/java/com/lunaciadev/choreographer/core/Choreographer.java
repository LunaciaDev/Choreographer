package com.lunaciadev.choreographer.core;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import com.badlogic.gdx.utils.Queue;
import com.lunaciadev.choreographer.data.ItemData;
import com.lunaciadev.choreographer.types.Crate;
import com.lunaciadev.choreographer.types.QueueType;
import com.lunaciadev.choreographer.types.Truck;
import com.lunaciadev.choreographer.utils.Inputable;
import com.lunaciadev.choreographer.utils.Signal;

/**
 * We now approach the meat of the program -Lunacia
 * 
 * The Choreographer is the one who will instruct the dance. Need to be tested
 * throughly.
 * 
 * We divide the incoming crate into 6 sets based on their catagory, sorted by
 * the queueGoal.
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

        public int dequeue(QueueType queueType) {
            switch (queueType) {
                case HEAVY_AMMO:
                    if (heavyAmmoQueue.isEmpty()) {
                        return -1;
                    }
                    else {
                        Crate target = heavyAmmoQueue.first();

                        if (target.queueManufactured()) {
                            heavyAmmoQueue.removeFirst();
                        }

                        return target.getId();
                    }
                case HEAVY_ARMS:
                    if (heavyArmQueue.isEmpty()) {
                        return -1;
                    }
                    else {
                        Crate target = heavyArmQueue.first();

                        if (target.queueManufactured()) {
                            heavyArmQueue.removeFirst();
                        }

                        return target.getId();
                    }
                case MATERIALS:
                    if (materialsQueue.isEmpty()) {
                        return -1;
                    }
                    else {
                        Crate target = materialsQueue.first();

                        if (target.queueManufactured()) {
                            materialsQueue.removeFirst();
                        }

                        return target.getId();
                    }
                case MEDICAL:
                    if (medicalQueue.isEmpty()) {
                        return -1;
                    }
                    else {
                        Crate target = medicalQueue.first();

                        if (target.queueManufactured()) {
                            medicalQueue.removeFirst();
                        }

                        return target.getId();
                    }
                case LIGHT_ARMS:
                    if (lightArmQueue.isEmpty()) {
                        return -1;
                    }
                    else {
                        Crate target = lightArmQueue.first();

                        if (target.queueManufactured()) {
                            lightArmQueue.removeFirst();
                        }

                        return target.getId();
                    }
                case UNIFORMS:
                    if (uniformsQueue.isEmpty()) {
                        return -1;
                    }
                    else {
                        Crate target = uniformsQueue.first();

                        if (target.queueManufactured()) {
                            uniformsQueue.removeFirst();
                        }

                        return target.getId();
                    }
                case UTILITIES:
                if (utilitiesQueue.isEmpty()) {
                    return -1;
                }
                else {
                    Crate target = utilitiesQueue.first();

                    if (target.queueManufactured()) {
                        utilitiesQueue.removeFirst();
                    }

                    return target.getId();
                }
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
            Queue<Crate> temp;

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
                default:
                    // this realistically should NOT happen
                    // the checker is bugging me about temp not being initialized..
                    temp = null;
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

    private class CompareByQueue implements Comparator<Crate> {
        @Override
        public int compare(Crate o1, Crate o2) {
            return o2.getQueueNeeded() - o1.getQueueNeeded();
        }
    }

    private class CompareByPriority implements Comparator<Crate> {
        @Override
        public int compare(Crate o1, Crate o2) {
            return o1.getPriority() - o2.getPriority();
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
     * @param status {@code Boolean} True if the request was handled successfully, False otherwise.
     */
    public Signal queueRequestComplete;

    /**
     * Emitted after an UndoRequest.
     * 
     * @param status {@code Boolean} True if the request was handled successfully, False otherwise.
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

    public void setData(Inputable dataSource) {
        crateMapping = dataSource.getData();

        ArrayList<Crate> lightArmQueue = new ArrayList<>();
        ArrayList<Crate> heavyArmQueue = new ArrayList<>();
        ArrayList<Crate> heavyAmmoQueue = new ArrayList<>();
        ArrayList<Crate> utilitiesQueue = new ArrayList<>();
        ArrayList<Crate> medicalQueue = new ArrayList<>();
        ArrayList<Crate> uniformsQueue = new ArrayList<>();
        ArrayList<Crate> materialsQueue = new ArrayList<>();

        for (Crate crate: crateMapping.values()) {
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

        CompareByQueue compareByQueue = new CompareByQueue();
        CompareByPriority compareByPriority = new CompareByPriority();

        // thanks the star java sort are stable

        lightArmQueue.sort(compareByQueue);
        lightArmQueue.sort(compareByPriority);
        heavyArmQueue.sort(compareByQueue);
        heavyArmQueue.sort(compareByPriority);
        heavyAmmoQueue.sort(compareByQueue);
        heavyAmmoQueue.sort(compareByPriority);
        utilitiesQueue.sort(compareByQueue);
        utilitiesQueue.sort(compareByPriority);
        medicalQueue.sort(compareByQueue);
        medicalQueue.sort(compareByPriority);
        uniformsQueue.sort(compareByQueue);
        uniformsQueue.sort(compareByPriority);
        materialsQueue.sort(compareByQueue);
        materialsQueue.sort(compareByPriority);

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
            queueRequestComplete.emit(false);
            return -1;
        }

        if (!truckQueue.last().addItem(id)) {
            truckQueue.addLast(new Truck(itemData));
            truckQueue.last().addItem(id);
        }

        queueRequestComplete.emit(true);
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
    }

    public boolean onCheckFinished() {
        if (queueManager.isFinished()) {
            reachedManuGoal.emit((Object) null);
            return true;
        }

        return false;
    }

    public void getTruckQueue() {
        returnTruckQueue.emit(truckQueue);
    }
}
