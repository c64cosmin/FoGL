
public class Main {
    public static void main(String[] args){
        if(args.length == 0)new FoGL();
        else if(args[0].equals("--sprite"))new SoGL();
    }
}
