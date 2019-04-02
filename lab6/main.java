public class main {

    public static void main(String[] args) {
         UnionFind testUnion = new UnionFind(15);
         testUnion.union(2,3);
         testUnion.union(3,4);
         testUnion.union(1,2);
         testUnion.union(7,8);
         testUnion.union(11,7);
         testUnion.union(11,2);
         testUnion.union(11,4);
         testUnion.print();


    }
}
