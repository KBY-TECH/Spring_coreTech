package core.core_tech.Singleton;

public class statefulService {
//    private int price; //상태 유지하는 필드

    public int order(String name,int price)
    {
        System.out.println("name : "+name+"price :"+price);
        return price;
    }

//    public int getPrice()
//    {
//        return this.price;
//    }
}
