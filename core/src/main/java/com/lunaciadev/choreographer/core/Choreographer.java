package com.lunaciadev.choreographer.core;

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
    private Queue<Truck> truckQueue;
    private HashMap<Integer, Crate> crateMapping;
    private ItemData itemData;

    /**
     * Emitted after a QueueReqeust. Return a single Boolean.
     * 
     * True if the request was handled successfully, False otherwise.
     */
    public Signal queueRequestComplete;

    /**
     * Emitted after an UndoRequest. Return a single Boolean.
     * 
     * True if the request was handled successfully, False otherwise.
     */
    public Signal undoRequestComplete;

    /**
     * Emitted after a checkFinished, if and only if all queue are empty.
     * 
     * No return value.
     */
    public Signal reachedManuGoal;

    public Choreographer(ItemData itemData) {
        queueManager = new QueueManager();
        truckQueue = new Queue<>();
        truckQueue.addFirst(new Truck(itemData));
        this.itemData = itemData;
    }

    public void setData(Inputable dataSource) {
        this.crateMapping = dataSource.getData();
    }

    public void onQueueRequest(QueueType queue) {
        int id = queueManager.dequeue(queue);

        if (id == -1) {
            queueRequestComplete.emit(false);
            return;
        }

        if (!truckQueue.last().addItem(id)) {
            truckQueue.addLast(new Truck(itemData));
            truckQueue.last().addItem(id);
        }

        queueRequestComplete.emit(true);
    }

    public void onUndoRequest() {
        int id = truckQueue.last().removeLastAdded();

        if (id == -1) {
            undoRequestComplete.emit(false);
        }

        if (truckQueue.last().isEmpty()) {
            truckQueue.removeLast();
        }

        Crate target = crateMapping.get(id);

        if (target.isCompleted()) {
            queueManager.enqueue(itemData.getQueueType(id), target);
        }
        target.undoQueueManufactured();
        undoRequestComplete.emit(true);
    }

    public void onCheckFinished() {
        if (queueManager.isFinished()) 
            reachedManuGoal.emit((Object) null);
    }
}
