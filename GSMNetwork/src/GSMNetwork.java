import java.io.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.StringTokenizer;

public class GSMNetwork {
    private final InputReader reader;
    private final OutputWriter writer;

    public GSMNetwork(InputReader reader, OutputWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public static void main(String[] args) {
        InputReader reader = new InputReader(System.in);
        OutputWriter writer = new OutputWriter(System.out);
        new GSMNetwork(reader, writer).run();
        writer.writer.flush();
    }

    class Edge {
        int from;
        int to;
    }

    class ConvertGSMNetworkProblemToSat {
        int numVertices;
        Edge[] edges;

        ConvertGSMNetworkProblemToSat (int n, int m) {
            numVertices = n;
            edges = new Edge[m];
            for (int i = 0; i < m; ++i) {
                edges[i] = new Edge();
            }
        }

        void printEquisatisfiableSatFormula() {
            ArrayList<ArrayList<Integer>> sol = new ArrayList<>();
            Integer[][] booleanIndices = new Integer[numVertices][3];
            for(int i=0;i<numVertices;i++){
                for(int j=0;j<3;j++){
                    booleanIndices[i][j]=i*3+j+1;
                }
                sol.addAll(exactlyOneOf(booleanIndices[i]));
            }
            for(int i=0;i<edges.length;i++){
                sol.addAll(
                        connectedNodes(
                            booleanIndices[edges[i].from-1], 
                            booleanIndices[edges[i].to-1]
                        )
                );
            }
//            boolean satisfiable = SatTester.naiveSatTester(sol);
            writer.printf("%d %d\n", sol.size(),booleanIndices.length*3);
            for(ArrayList<Integer> clause:sol){
                for(Integer b:clause){
                    writer.printf("%d ", b);
                }
                writer.printf("0\n");
            }
            // This solution prints a simple satisfiable formula
            // and passes about half of the tests.
            // Change this function to solve the problem.
//            writer.printf("3 2\n");
//            writer.printf("1 2 0\n");
//            writer.printf("-1 -2 0\n");
//            writer.printf("1 -2 0\n");
//            Integer[][] sat = new Integer[numVertices][3];
//            int count=0;
//            for(int i=0;i<sat.length;i++){
//                for(int j=0;j<sat[i].length;j++){
//                    sat[i][j]=count;
//                    count++;
//                }   
//            }
        }
    }
        
    public void run() {
        int n = reader.nextInt();
        int m = reader.nextInt();

        GSMNetwork.ConvertGSMNetworkProblemToSat  converter = new GSMNetwork.ConvertGSMNetworkProblemToSat (n, m);
        for (int i = 0; i < m; ++i) {
            converter.edges[i].from = reader.nextInt();
            converter.edges[i].to = reader.nextInt();
        }

        converter.printEquisatisfiableSatFormula();
    }

    

//        boolean exactlyOneOf(BitSet node){
//            return node.cardinality()==1;
//        }
//
//        boolean connectedNodes(BitSet nodeA, BitSet nodeB){
//            return nodeA.intersects(nodeB);
//        }


        ArrayList<ArrayList<Integer>> exactlyOneOf(Integer[] row){
            if(row.length>3){
                System.out.println("Wrong size row");
                return new ArrayList<>();
            }
            ArrayList<ArrayList<Integer>> rtrn = new ArrayList<>();
            ArrayList<Integer> tmpList = new ArrayList<>();
            for(int i=0;i<row.length;i++)
                tmpList.add(row[i]);
            rtrn.add(tmpList);
            for(int i=0;i<3;i++){
                //this only works with groups 3 or less!
                ArrayList<Integer> tempArray = new ArrayList<>();
                tempArray.add(row[i]*-1);
                tempArray.add(row[(i+1)%row.length]*-1);
                rtrn.add(tempArray);
            }
            return rtrn;
        }
        ArrayList<ArrayList<Integer>> connectedNodes(Integer[] nodeA, Integer[] nodeB){
           if(nodeA.length!=nodeB.length){
               return new ArrayList();
           }
           ArrayList<ArrayList<Integer>> rtrn = new ArrayList<>();
           for(int i=0;i<nodeA.length;i++){
               ArrayList<Integer> tmp = new ArrayList<>();
               tmp.add(nodeA[i]*-1);
               tmp.add(nodeB[i]*-1);
               rtrn.add(tmp);
           }
           return rtrn;
        }
    

//void printEquisatisfiableFormula(){
//
//}
    


    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public double nextDouble() {
            return Double.parseDouble(next());
        }

        public long nextLong() {
            return Long.parseLong(next());
        }
    }

    static class OutputWriter {
        public PrintWriter writer;

        OutputWriter(OutputStream stream) {
            writer = new PrintWriter(stream);
        }

        public void printf(String format, Object... args) {
            writer.print(String.format(Locale.ENGLISH, format, args));
        }
    }
}
