// Hannes Kullberg Ekberg, haku6773
import java.util.Scanner;
import java.io.InputStream;
import java.util.ArrayList;

public class InputScan {
    private static ArrayList<InputStream> input = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);
    
    public InputScan(){
        this(System.in);
    }
    
    public InputScan(InputStream in) {
        if(input.contains(in)){
            throw new IllegalStateException("ERROR");
        }
        this.scanner = new Scanner(in);
        input.add(in);
    }

    public String printString(String string){
        System.out.print(string + "?>");
        String str = scanner.nextLine();
        while(str.isBlank()){
            System.out.println("Error, try again!");
            str = scanner.nextLine();
        }
        str = str.trim().toLowerCase();
        str = str.substring(0, 1).toUpperCase() + str.substring(1);
        return str;
    }

    public int printInt(String string){
        System.out.print(string+"?>");
        int number = scanner.nextInt();
        scanner.nextLine();
        return number;
    }

    public double printDouble(String string){
        System.out.print(string+"?>");
        double decimal = scanner.nextDouble();
        scanner.nextLine();
        return decimal;
    }

}