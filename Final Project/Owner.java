//hannes kullberg ekberg, haku6773

import java.util.Arrays;

public class Owner {
    
    private static final int INITIAL_MAX_DOGS = 0;
    private int numDogs;
    private int maxDogs;
    private Dog[] ownedDogList;
    private String name;

    public Owner(String name){
        this.numDogs = 0;
        this.maxDogs = INITIAL_MAX_DOGS;
        this.name = name;
        this.ownedDogList = new Dog[maxDogs];
    }

    public String getName(){
        return name;
    }

    public boolean doesOwnerOwnDog(Dog d){
        for(Dog i: ownedDogList){
            if(i == d){
                return true;
            }
        }
        return false;
    }

    public void giveDog(Dog d){
        if(d.hasOwner() && d.getOwner() != this || this.doesOwnerOwnDog(d)){
            return;
        }
        if(numDogs == maxDogs){
            maxDogs++;
            ownedDogList = Arrays.copyOf(ownedDogList, maxDogs);
        }
        ownedDogList[numDogs]=d;
        numDogs++;
        if(d.getOwner() == this){
            return;
        }
        d.giveOwner(this);
    }

    public void removeDog(Dog d){
        if(!this.doesOwnerOwnDog(d)){
            return;
        }
        
        int index = indexOfDog(d);
        if(index == numDogs-1){
            ownedDogList[index] = null;
        }else{
            for(int i = index; i<numDogs-1; i++){
                ownedDogList[i] = ownedDogList[i+1];
            }
            ownedDogList[numDogs-1] = null;
        }
        numDogs--;
        d.removeOwner();
    }

    private int indexOfDog(Dog d){
        int index = 0;
        for(int i = index; i<ownedDogList.length; i++){
            if(ownedDogList[i] == d){
                index = i;
            }
        }
        return index;
    }

    @Override
    public String toString(){
        String str = name + " owns " + Arrays.toString(ownedDogList);
        if(ownedDogList.length == 0){
            str = name + " (Doesn't own any dogs)";
        }
        return str;
    }
}
