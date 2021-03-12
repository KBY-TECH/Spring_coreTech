package core.core_tech.Singleton;

public class singletonService   {
    private static final  singletonService ins=new singletonService(); // 자기 자신 내부에 private 에 하나 static 하나에 올라옴

    // 조횟 사용
    public static  singletonService getIns(){
        return ins;
    }

     private singletonService() {
    }

    public void logic(){}
    {
        System.out.println("싱글톤 객체 로직 호출.");
    }
}
