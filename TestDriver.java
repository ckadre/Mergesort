public class TestDriver 
{
    public static void main(String[] args)
    {
        IUDoubleLinkedList<String> list = new IUDoubleLinkedList<String>();
        
        list.add("arthur");
        list.add("faroe");
        list.add("john");
       // list.add("yellow");
     //   list.add("kayne");
      //  list.add("bella");
       // list.add("bella");
        System.out.println(list.toString());
        Sort.sort(list);
        System.out.println(list.toString());
    }
}
