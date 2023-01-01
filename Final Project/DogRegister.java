import java.util.ArrayList;
import java.util.Collections;

public class DogRegister {

    private ArrayList<Dog> dogList = new ArrayList<>();
    private ArrayList<Owner> ownerList = new ArrayList<>();
    private InputScan input; 
    private boolean isRunning = true;
    
    public DogRegister(){
        dogList = new ArrayList<>();
        ownerList = new ArrayList<>();
        input = new InputScan();
        showMenu();
        runLoop();
    }
    public static void main(String[] args) {
        new DogRegister();
    }

    private void runLoop(){
        String command;
        do {
            command = readCommand();
            handleCommandLoop(command);
        } while (isRunning);
    }

    private String readCommand(){
        return input.printString("Command");
    }

    private void handleCommandLoop(String command){
        switch(command){
            case "Register new dog":
                registerDog();
                break;
            case "List dogs":
                listDogs();
                break;
            case "Increase age":
                increaseAge();
                break;
            case "Register new owner":
                registerNewOwner();
                break;
            case "Give dog":
                giveDog();
                break;
            case "List owners":
                listOwner();
                break;
            case "Remove dog":
                removeDog();
                break;
            case "Remove owned dog":
                removeOwnedDog();
                break;
            case "Remove owner":
                removeOwner();
                break;
            case "Exit":
                isRunning = false;
                break;
            default:
                System.out.println("Error! invalid command");
                showMenu();
        }
    }

    private void showMenu(){
        System.out.println("Available commands:\nRegister new dog?>\nList dogs?>\nIncrease age?>\nRegister new dog?>\nGive dog?>\nList owners?>\nRemove dog?>\nRemove owned dog?>\nRemove owner?>\nExit?>");
    }
    
    private void registerDog(){
        String name = input.printString("Name");
        String breed = input.printString("Breed");
        int age = input.printInt("Age");
        int weight = input.printInt("Weight");
        Dog newDog = new Dog(name, breed, age, weight);
        dogList.add(newDog);
    }

    private void listDogs(){
        if (dogList.isEmpty()) {
            System.out.println("Error! No dogs in register!");
            return;
        }

        double inputTailLength = input.printDouble("Enter minimum tail length");
        ArrayList<Dog> relevantDogs = getRelevantTail(inputTailLength);

        if (relevantDogs.isEmpty()) {
            System.out.println("Error! No dogs with long enough tail!");
            return;
        }
        
        ArrayList<Dog> sortedList = sort(relevantDogs);
        System.out.println("The following dogs has such a large tail:");
        for (int i = 0; i < sortedList.size(); i++) {
            System.out.println(sortedList.get(i));
        }
    }

    private void removeDog(){
        String dogName = input.printString("Enter the name of the dog?>");
        Dog dog = findDog(dogName);
        if(dog == null){
            System.out.println("Error! Dog doesn't exist in register");
            return;
        }
        System.out.println(dogName + " is now removed from the register");
        dog.removeOwner();
        dogList.remove(dog);
    }

    private void increaseAge(){
        String name = input.printString("Enter the name of the dog?>");
        Dog dogName = findDog(name);

        if(dogList.contains(dogName)){
            dogName.increaseAge();
            System.out.println(dogName + " is now 1 year older");
            return;
        }

        if(!dogList.contains(dogName)){
            System.out.println("Error! Dog doesn't exist in register");
        }
    }

    private void registerNewOwner(){
        String name = input.printString("Name");
        Owner newOwner = new Owner(name);
        ownerList.add(newOwner);
        System.out.println(name + " is now registered");
    }

    private void giveDog(){
        String dogName = input.printString("Enter the name of the dog");
        Dog dog = findDog(dogName);
        
        if(dog == null){
            System.out.println("Error! Dog doesn't exist in register");
            return;
        }
        
        if(dog.hasOwner()){
            System.out.println("Error! Dog already has owner");
            return;
        }
        
        String ownerName = input.printString("Enter the name of the owner");
        Owner owner = findOwner(ownerName);
        
        if(owner == null){
            System.out.println("Error! No such owner");
            return;
        }
        
        if(dog.hasOwner() && dog.getOwner() != owner || owner.doesOwnerOwnDog(dog)){
            System.out.println("Error! Dog already has owner");
            return;
        }

        owner.giveDog(dog);
        System.out.println(ownerName + " now owns " + dogName);
    }

    private void listOwner(){
        if (ownerList.isEmpty()) {
            System.out.println("Error! No owners in register!");
            return;
        }

        for (Owner i : ownerList) {
            System.out.println(i + " ");
        }
    }

    private void removeOwnedDog(){
        String dogName = input.printString("Enter the name of the dog");
        Dog dog = findDog(dogName);

        if(dog == null){
            System.out.println("Error! Dog doesn't exist in register");
            return;
        }

        if(!dog.hasOwner()){
            System.out.println("Error! " + dogName + " is not owned by anyonw");
            return;
        }

        String ownerName = dog.getOwner().getName();
        dog.removeOwner();
        System.out.println(dogName + " is now removed from "+ ownerName);
    }

    private void removeOwner(){
        String ownerName = input.printString("Enter the name of the owner");
        Owner owner = findOwner(ownerName);
        if(owner == null){
            System.out.println("Error! Dog doesn't exist in register");
            return;
        }
        dogList.removeIf(d -> d.getOwner() == owner);
        ownerList.remove(owner);
        System.out.println(ownerName + " is now removed from the register!");
    }

    private ArrayList<Dog> getRelevantTail(double tailLength) {
        ArrayList<Dog> relevantDogs = new ArrayList<Dog>();
        for (Dog d : dogList) {
            if (d.getTailLength() >= tailLength) {
                relevantDogs.add(d);
            }
        }
        return relevantDogs;
    }

    private Dog findDog(String dogName){
        Dog foundDog = null;
        for(Dog name : dogList){
            if (name.getName().equalsIgnoreCase(dogName)){
                foundDog = name;
            }
        }
        return foundDog;
    }
    
    private Owner findOwner(String name){
        Owner foundOwner = null; 
        for(Owner i: ownerList){
            if(i.getName().equalsIgnoreCase(name)){
                foundOwner = i;
            }
        }
        return foundOwner;
    }

    private ArrayList<Dog> sort(ArrayList<Dog> list){
        for(int i = 0; i<list.size(); i++){
            int minIndex = getSmallestDog(list, i);
            if(minIndex != i){
                swap(list, i, minIndex);
            }
        }
        return list;
    }

    private void swap(ArrayList<Dog> listToSort, int i, int j){
        Collections.swap(listToSort, i, j);
    }
    
    private int compareTailLength(double i, double j){
        return Double.compare(i,j);
    }

    private int compareDogName(String i, String j){
        return i.compareToIgnoreCase(j);
    }

    private int compareDog(Dog a, Dog b){
        int resultTailLength = compareTailLength(a.getTailLength(),b.getTailLength());
        if(resultTailLength == 0){
            return compareDogName(a.getName(),b.getName());
        }else{
            return resultTailLength;
        }
    }

    private int getSmallestDog(ArrayList<Dog> listToSort, int index){
        int minIndex = index;
        for(int i = index; i<listToSort.size(); i++){
            if(compareDog(listToSort.get(i),listToSort.get(minIndex)) < 0){
                minIndex = i;
            }
        }
        return minIndex;
    }

}
