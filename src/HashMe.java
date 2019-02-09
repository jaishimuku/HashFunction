
import java.io.*;
import java.util.Scanner;

public class HashMe {
       public class NameAndHashIndex{
           String Name;
           int HashKey;

           NameAndHashIndex(String Name, int HashKey){
               this.Name = Name;
               this.HashKey = HashKey;
           }

           public String getName(){
               return Name;
           }
           public int getHashKey(){
               return HashKey;
           }

           public void setName(String Name) {
               this.Name = Name;
           }
       }

       NameAndHashIndex[] HashTable;
       static int NoOfClashes=0, size;

    HashMe(int LengthOfTable){
        HashTable = new NameAndHashIndex[LengthOfTable];
        int i;
        for (i = 0; i<HashTable.length; i++){
            HashTable[i] = new NameAndHashIndex("",-1);
        }
    }

    public int BuildHashIndex(String name){
        int nameHash = (((name.charAt(0)-97)* 26 *26)+
                       ((name.charAt(1)-97)*26)+
                       ((name.charAt(2)-97)*1));

        if (HashTable.length == 200){      // (17500/200) ~= 90
            return nameHash/100;

        }else if(HashTable.length == 400){  // (17500/400) ~= 45
            return nameHash/45;

        }else if (HashTable.length == 700){  //17500/700 =25
            return  nameHash/25;
        }else {
            return -1;
        }
      /* if( HashTable.length == 17500){
           return nameCode;
        }else {
           return -1;
       }*/
    }

    public void AddToTable(String name){
        int HashIndex = BuildHashIndex(name);

        if (HashTable[HashIndex].getHashKey() == -1){
            HashTable[HashIndex] = new NameAndHashIndex(name, HashIndex);
        }else {
           while (HashTable[HashIndex].HashKey != -1){
                HashIndex +=1;
               NoOfClashes++;
           }
            HashTable[HashIndex] = new NameAndHashIndex(name, HashIndex);
        }
    }

    public void ShowHashTable(){
        System.out.println("Names    |      HashIndex");
        for (int i=0; i<HashTable.length; i++){
            if (HashTable[i].HashKey != -1){
                System.out.println(HashTable[i].Name+"--------------"+HashTable[i].HashKey);
            }
        }
        System.out.println("--------------------------------");
        System.out.println("Total Number of Hash Clashes is: " + NoOfClashes);
    }

    public static void main(String[] args) {
        Scanner scanner =new Scanner(System.in);
        System.out.println(" Select a size of HashTable: ");
        System.out.println(
                "    1: For 200 size HashTable\n"+
                "    2: For 400 size HashTable\n"+
                "    3: For 700 size HashTable"
                );
        System.out.print("ENTER: ");
        int n = scanner.nextInt();

        switch (n){
            case 1:
                size=200;
                break;
            case 2:
                size=400;
                break;
            case 3:
                size=700;
                break;
        }

        HashMe value = new HashMe(size);

        try {
            BufferedReader input = new BufferedReader(new FileReader("names.txt"));

            String line = input.readLine();

            while (line != null){
                if ("" == line ){
                    input.close();
                }else {
                    value.AddToTable(line);
                    line = input.readLine();
                }
            }
        }
        catch (Exception e){
            System.out.println("Errors" +e);
        }
        boolean loop = true;
        while (loop){
            System.out.println();
            System.out.println(
                            "SELECT 1: Show HashTable.\n" +
                            "SELECT 2: Number of Clashes\n" +
                            "SELECT 3: Terminate Program"
            );

            int input = scanner.nextInt();
            switch (input){
                case 1: value.ShowHashTable();
                break;
                case 2:
                    System.out.print("Total Number of Clashes on size of "+ size +" HashTable are: "+NoOfClashes);
                    break;
                case 3: System.exit(0);
            }
        }
    }
}