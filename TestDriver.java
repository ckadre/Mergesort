public class TestDriver 
{
    public static void main(String[] args)
    {
        WrappedDLL<String> list = new WrappedDLL<String>();
        list.add("john");
        list.add("arthur");
        list.add("faroe");
        list.add("yellow");
        list.add("kayne");
        list.add("hastur");
        System.out.println(list.toString());
        Sort.sort(list);
        System.out.println(list.toString());
    }
}
