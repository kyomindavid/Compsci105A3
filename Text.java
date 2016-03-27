import java.io.*;
import java.util.*;
 
/**
        COMPSCI 105 S1 C, 2014
        Assignment Three Question 1
 
        @author        
        @ID            
        @version        06/06/2014
 **/
 
public class Text {
 
   /**
         * This method generates the huffman tree for the text: "abracadabra!"
         *
         * @return the root of the huffman tree
         */
        public static TreeNode<CharFreq> abracadbraTree() {
                TreeNode<CharFreq> n0 = new TreeNode<CharFreq>(new CharFreq('!', 1));
                TreeNode<CharFreq> n1 = new TreeNode<CharFreq>(new CharFreq('c', 1));
                TreeNode<CharFreq> n2 = new TreeNode<CharFreq>(new CharFreq('\u0000', 2), n0, n1);
                TreeNode<CharFreq> n3 = new TreeNode<CharFreq>(new CharFreq('r', 2));
                TreeNode<CharFreq> n4 = new TreeNode<CharFreq>(new CharFreq('\u0000', 4), n3, n2);
                TreeNode<CharFreq> n5 = new TreeNode<CharFreq>(new CharFreq('d', 1));
                TreeNode<CharFreq> n6 = new TreeNode<CharFreq>(new CharFreq('b', 2));
                TreeNode<CharFreq> n7 = new TreeNode<CharFreq>(new CharFreq('\u0000', 3), n5, n6);
                TreeNode<CharFreq> n8 = new TreeNode<CharFreq>(new CharFreq('\u0000', '7'), n7, n4);
                TreeNode<CharFreq> n9 = new TreeNode<CharFreq>(new CharFreq('a', 5));
                TreeNode<CharFreq> n10 = new TreeNode<CharFreq>(new CharFreq('\u0000', 12), n9, n8);
                return n10;
        }
 
   /**
         * This method decompresses a huffman compressed text file.  The compressed
         * file must be read one bit at a time using the supplied BitReader, and
         * then by traversing the supplied huffman tree, each sequence of compressed
         * bits should be converted to their corresponding characters.  The
         * decompressed characters should be written to the FileWriter
         *
         * @param  br:      the BitReader which reads one bit at a time from the
         *                  compressed file
         *         huffman: the huffman tree that was used for compression, and
         *                  hence should be used for decompression
         *         fw:      a FileWriter for storing the decompressed text file
         */
        public static void decompress(BitReader br, TreeNode<CharFreq> huffman, FileWriter fw) throws Exception {      
                TreeNode<CharFreq> currentTreeNode = huffman;
                while (br.hasNext()) {
                        if (br.next()) {
                                currentTreeNode = currentTreeNode.getRight();
                        }else{
                                currentTreeNode =  currentTreeNode.getLeft();
                        }
                        if (currentTreeNode.isLeaf()) {
                                fw.write(currentTreeNode.getItem().getChar());
                                currentTreeNode = huffman;
                        }
                }
        fw.flush();
        }
 
   /**
         * This method traverses the supplied huffman tree and prints out the codes
         * associated with each character
         *
         * @param t
         *            : the root of the huffman tree to be traversed code: a String
         *            used to build the code for each character as the tree is
         *            traversed recursively
         */
        public static void traverse(TreeNode<CharFreq> t, String code) {
                if(t.isLeaf()){
                        System.out.println(t.getItem().getChar() + " : " + code);
                return;
                }if (t.getLeft() != null){
                        traverse(t.getLeft(),code + "0");
                }if (t.getRight() != null){
                        traverse(t.getRight(),code + "1");
                }      
        }
 
   /**
         * This method traverses the supplied huffman tree and stores the codes
         * associated with each character in the supplied array.
         *
         * @param t
         *            : the root of the huffman tree to be traversed code: a String
         *            used to build the code for each character as the tree is
         *            traversed recursively codes: an array to store the code for
         *            each character. The indexes of this array range from 0 to 127
         *            and represent the ASCII value of the characters.
         */
        public static void traverse(TreeNode<CharFreq> t, String code, String[] codes) {
                if(t.isLeaf()){
                        codes[t.getItem().getChar()] = (code);
                return;
                }if (t.getLeft() != null){
                        traverse(t.getLeft(),code + "0", codes);
                }if (t.getRight() != null){
                        traverse(t.getRight(),code + "1", codes);
                }              
        }
 
   /**
         * This method removes the TreeNode, from an ArrayList of TreeNodes,  which
         * contains the smallest item.  The items stored in each TreeNode must
         * implement the Comparable interface.
         * The ArrayList must contain at least one element.
         *
         * @param  a: an ArrayList containing TreeNode objects
         *
         * @return the TreeNode in the ArrayList which contains the smallest item.
         *         This TreeNode is removed from the ArrayList.
         */
        public static TreeNode<CharFreq> removeMin(ArrayList<TreeNode<CharFreq>> a) {
                int minIndex = 0;
                for (int i = 0; i < a.size(); i++) {
                        TreeNode<CharFreq> ti = a.get(i);
                        TreeNode<CharFreq> tmin = a.get(minIndex);
                        if ((ti.getItem()).compareTo(tmin.getItem()) < 0)
                                minIndex = i;
                }
                TreeNode<CharFreq> n = a.remove(minIndex);
        return n;
        }
       
   /**
         * This method counts the frequencies of each character in the supplied
         * FileReader, and produces an output text file which lists (on each line)
         * each character followed by the frequency count of that character. This
         * method also returns an ArrayList which contains TreeNodes. The item
         * stored in each TreeNode in the returned ArrayList is a CharFreq object,
         * which stores a character and its corresponding frequency
         *
         * @param fr
         *            : the FileReader for which the character frequencies are being
         *            counted pw: the PrintWriter which is used to produce the
         *            output text file listing the character frequencies
         *
         * @return the ArrayList containing TreeNodes. The item stored in each
         *         TreeNode is a CharFreq object.
         */
        public static ArrayList<TreeNode<CharFreq>> countFrequencies(FileReader fr,PrintWriter pw) throws Exception {
                ArrayList<TreeNode<CharFreq>> arrayListOfTreeNodes = new ArrayList<TreeNode<CharFreq>>();
                BufferedWriter bw = new BufferedWriter(pw);
                int[] charArray = new int[128];
                for (int i : charArray)i = 0;
                int readCharacter;
                while ((readCharacter = fr.read()) != -1) {
                        charArray[readCharacter]++;
                }
                for (int i = 0; i < charArray.length; i++) {
                        if (charArray[i] != 0)
                                bw.write((char) i + " " + charArray[i] + "\n");
                }
                bw.flush();
                for (int i = 0; i < charArray.length; i++) {
                        if (charArray[i] != 0)
                                arrayListOfTreeNodes.add(new TreeNode<CharFreq>(new CharFreq((char) i,charArray[i])));
                }
        return arrayListOfTreeNodes;
        }
 
   /**
         * This method builds a huffman tree from the supplied ArrayList of TreeNodes.
         * Initially, the items in each TreeNode in the ArrayList store a CharFreq object.
         * As the tree is built, the smallest two items in the ArrayList are removed,
         * merged to form a tree with a CharFreq object storing the sum of the frequencies
         * as the root, and the two original CharFreq objects as the children.  The right
         * child must be the second of the two elements removed from the ArrayList (where
         * the ArrayList is scanned from left to right when the minimum element is found).
         * When the ArrayList contains just one element, this will be the root of the
         * completed huffman tree.
         *
         * @param  trees: the ArrayList containing the TreeNodes used in the algorithm
         *                for generating the huffman tree
         *
         * @return the TreeNode referring to the root of the completed huffman tree
         */
        public static TreeNode<CharFreq> buildTree(ArrayList<TreeNode<CharFreq>> trees) throws IOException {
                TreeNode<CharFreq> firstMinFrequency;
                TreeNode<CharFreq> secondMinFrequency;
                int intialNodeNumber = 0;
                while (trees.size() > 1){
                        firstMinFrequency = trees.get(findMinIndex(trees));
                        trees.remove(findMinIndex(trees));
                        secondMinFrequency = trees.get(findMinIndex(trees));
                        trees.remove(findMinIndex(trees));
                        trees.add((new TreeNode<CharFreq>(new CharFreq('\u0000', firstMinFrequency.getItem().getFreq() + secondMinFrequency.getItem().getFreq()),firstMinFrequency, secondMinFrequency)));
                }      
        return trees.get(0);
        }
       
        public static int findMinIndex(ArrayList<TreeNode<CharFreq>> trees){
                int minIndex = 0;
                for (int i = 0; i < trees.size(); i++) {
                        if(trees.get(minIndex).getItem().compareTo(trees.get(i).getItem()) > 0){
                                minIndex = i;
                        }
                }
        return minIndex;
        }
               
   /**
         * This method compresses a text file using huffman encoding. Initially, the
         * supplied huffman tree is traversed to generate a lookup table of codes
         * for each character. The text file is then read one character at a time,
         * and each character is encoded by using the lookup table. The encoded bits
         * for each character are written one at a time to the specified BitWriter.
         *
         * @param fr
         *            : the FileReader which contains the text file to be encoded
         *            huffman: the huffman tree that was used for compression, and
         *            hence should be used for decompression bw: the BitWriter used
         *            to write the compressed bits to file
         */
        public static void compress(FileReader fr, TreeNode<CharFreq> huffman,BitWriter bw) throws Exception {
                String[] codes = new String[128];
                traverse(huffman, "", codes);
                int readCharacter;
                while ((int) (readCharacter = fr.read()) != -1) {
                        for (int i = 0; i < codes[readCharacter].length(); i++) {
                                bw.writeBit(Integer.parseInt("" + codes[readCharacter].charAt(i)));
                        }
                }
        }
 
   /**
         * This method reads a frequency file (such as those generated by the
         * countFrequencies() method) and initialises an ArrayList of TreeNodes
         * where the item of each TreeNode is a CharFreq object storing a character
         * from the frequency file and its corresponding frequency. This method
         * provides the same functionality as the countFrequencies() method, but
         * takes in a frequency file as parameter rather than a text file.
         *
         * @param inputFreqFile
         *            : the frequency file which stores characters and their
         *            frequency (one character per line)
         *
         * @return the ArrayList containing TreeNodes. The item stored in each
         *         TreeNode is a CharFreq object.
         */
        public static ArrayList<TreeNode<CharFreq>> readFrequencies(String inputFreqFile) throws Exception {
                BufferedReader br = new BufferedReader(new FileReader(inputFreqFile));
                int[] charArray = readFrequencies(br);
                ArrayList<TreeNode<CharFreq>> arrayListOfTreeNodes = new ArrayList<TreeNode<CharFreq>>();
                for (int i = 0; i < charArray.length; i++) {
                        if (charArray[i] != 0)
                                arrayListOfTreeNodes.add(new TreeNode<CharFreq>(new CharFreq((char)i,charArray[i])));
                }
        return arrayListOfTreeNodes;
        }
       
        public static int[] readFrequencies(BufferedReader br) throws Exception {
                int[] charArray = new int[128];
                int readCharacter;
                int frequency;
                while((readCharacter = br.read()) != -1){
                        br.read();
                        String freqString = "";
                        while ((frequency = br.read()-48) >=0 && (frequency <= 9)){
                                freqString += frequency;
                        }
                        frequency = Integer.parseInt(freqString);
                        charArray[readCharacter] = frequency;
                }              
        return charArray;
        }
 
   /**
         * This method prints out the sizes (in bytes) of the compressed and the
         * original files, and computes and prints out the compressed ratio.
         *
         * @param file1
         *            : full name of the first file file2: full name of the second
         *            file
         */
        public static void statistics(String file1, String file2) {
                String file1SizeInBytes, file2SizeInBytes;
                double compressedRatio;
                File file1size = new File(file1);
                File file2size = new File(file2);
                file1SizeInBytes = "" + file1size.length();
                file2SizeInBytes = "" + file2size.length();
                System.out.println("Size of the compressed file: " + file1SizeInBytes + " bytes" );
                System.out.println("Size of the original file: " + file2SizeInBytes + " bytes" );
                compressedRatio = Double.parseDouble(file1SizeInBytes) / Double.parseDouble(file2SizeInBytes) * 100;
                System.out.println("Compressed ratio: " + compressedRatio + "%" );
        }
 
   /*
         * This TextZip application should support the following command line flags:
         *
         * QUESTION 1 PART 1 ================= -a : this uses a default prefix code
         * tree and its compressed file, "a.txz", and decompresses the file, storing
         * the output in the text file, "a.txt". It should also print out the size
         * of the compressed file (in bytes), the size of the decompressed file (in
         * bytes) and the compression ratio
         *
         * QUESTION 1 PART 2 ================= -f : given a text file (args[1]) and
         * the name of an output frequency file (args[2]) this should count the
         * character frequencies in the text file and store these in the frequency
         * file (with one character and its frequency per line). It should then
         * build the huffman tree based on the character frequencies, and then print
         * out the prefix code for each character
         *
         * QUESTION 1 PART 3 ================= -c : given a text file (args[1]) and
         * the name of the output compressed file (args[2]) and the name of an
         * output frequency file (args[3]), this should compress the file
         *
         * QUESTION 1 PART 4 ================= -d : given a compressed file
         * (args[1]) and its corresponding frequency file (args[2]) and the name of
         * the output decompressed text file (args[3]), this should decompress the
         * file
         */
 
public static void main(String[] args) throws Exception {
 
                /* This is a standard sample command line implementation. If you choose to
                 * implement your program with a Graphical User Interface (GUI), please
                 * write your own implementation accordingly.
                */
               
                if (args[0].equals("-a")) {
                        BitReader br = new BitReader("a.txz");
                        FileWriter fw = new FileWriter("a.txt");
                        System.out.println("a.txz decompressed by zxcv");
                        // Get the default prefix code tree
                        TreeNode<CharFreq> tn = abracadbraTree();
                        // Decompress the default file "a.txz"
                        decompress(br, tn, fw);
                        // Close the output file
                        fw.close();
                        // Output the compression ratio
                        statistics("a.txz", "a.txt");
                }
                else if (args[0].equals("-f")) {
                        FileReader fr = new FileReader(args[1]);
                        PrintWriter pw = new PrintWriter(new FileWriter(args[2]));
                        System.out.println("a.txt prefix codes by zxcv");
                        // Calculate the frequencies from the .txt file
                        ArrayList<TreeNode<CharFreq>> trees = countFrequencies(fr, pw);
                        // Close the files
                        fr.close();
                        pw.close();
                        // Build the huffman tree
                        TreeNode<CharFreq> n = buildTree(trees);
                        // Display the codes
                        System.out.println("character code:");
                        traverse(n, "");
                }
                else if (args[0].equals("-c")) {
                        FileReader fr = new FileReader(args[1]);
                        PrintWriter pw = new PrintWriter(new FileWriter(args[3]));
                        System.out.println("file.txt compressed by zxcv ");
                        // Calculate the frequencies from the .txt file
                        ArrayList<TreeNode<CharFreq>> trees = countFrequencies(fr, pw);
                        fr.close();
                        pw.close();
                        // Build the huffman tree
                        TreeNode<CharFreq> n = buildTree(trees);
                        // Compress the .txt file
                        fr = new FileReader(args[1]);
                        BitWriter bw = new BitWriter(args[2]);
                        compress(fr, n, bw);
                        bw.close();
                        fr.close();
                        // Output the compression ratio
                        statistics(args[2], args[1]);
                }
                else if (args[0].equals("-d")) {
                        // Read in the frequencies from the .freq file
                        System.out.println("file.txz decompressed by zxcv");
                        ArrayList<TreeNode<CharFreq>> a = readFrequencies(args[2]);
                        // Build the huffman tree
                        TreeNode<CharFreq> tn = buildTree(a);
                        // Decompress the .txz file
                        BitReader br = new BitReader(args[1]);
                        FileWriter fw = new FileWriter(args[3]);
                        decompress(br, tn, fw);
                        fw.close();
                        // Output the compression ratio
                        statistics(args[1], args[3]);
                }
        } // end main method
}