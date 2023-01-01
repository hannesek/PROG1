// Hannes Kullberg Ekberg, haku6773

public class Dog{
    
    private static final double DACHSHUND_LENGTH = 3.7;
    private String breed;
    private String name;
    private int age;
    private int weight;
    private Owner owner;
    
    public Dog(String name, String breed, int age, int weight){
        this.name = name;
        this.breed = breed;
        this.age = age;
        this.weight = weight;
    }

    public String getName(){
        return name;
    }

    public String getBreed(){
        return breed;
    }

    public int getAge(){
        return age;
    }

    public int increaseAge(){
        return age++;
    }

    public int getWeight(){
        return weight;
    }

    public double getTailLength(){
        if (breed.equalsIgnoreCase("tax") || breed.equalsIgnoreCase("dachshund")){
            return DACHSHUND_LENGTH;
        }else{
            return (double)(age*weight)/10.0;
        }
    }

    public boolean hasOwner(){
        return owner != null;
    }

    public void giveOwner(Owner owner){
        if(hasOwner()){
            return;
        }
        this.owner = owner;
        if(!owner.doesOwnerOwnDog(this)){
            owner.giveDog(this);
        }

    }

    public Owner getOwner(){
        return owner;
    }

    public void removeOwner(){
        if(!hasOwner()){
            return;
        }
        Owner o = this.owner;
        this.owner = null;
        o.removeDog(this);
    }

    @Override
    public String toString(){
        String str =  name + " " + breed + " " + age + " " + weight + " " + getTailLength();
        if (getOwner() != null){
            return str + " " + getOwner().getName();
        }
        return str;
    }
}