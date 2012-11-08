
import java.util.Set;
import multi.patt.match.ac.*;

public class Demo {
	public static void main(String [] args){
		AcApply obj = new AcApply();
		String word1 = "apple";
		String text1 = "washington cut the apple tree";
		Set result1 = obj.findSingleWord(word1, text1);
		System.out.println(result1);
		System.out.println("===========================");

		String [] word2 = {"microsome", "cytochrome", "cytochrome P450 activity", "gibberellic acid biosynthesis", "GA3", "cytochrome P450", "oxygen binding", "AT5G25900.1", "protein", "RNA", "gibberellin", "Arabidopsis", "ent-kaurene oxidase activity", "inflorescence", "tissue"};
		String text2 = "The ga3 mutant of Arabidopsis is a gibberellin-responsive dwarf. We present data showing that the ga3-1 mutant is deficient in ent-kaurene oxidase activity, the first cytochrome P450-mediated step in the gibberellin biosynthetic pathway. By using a combination of conventional map-based cloning and random sequencing we identified a putative cytochrome P450 gene mapping to the same location as GA3. Relative to the progenitor line, two ga3 mutant alleles contained single base changes generating in-frame stop codons in the predicted amino acid sequence of the P450. A genomic clone spanning the P450 locus complemented the ga3-2 mutant. The deduced GA3 protein defines an additional class of cytochrome P450 enzymes. The GA3 gene was expressed in all tissues examined, RNA abundance being highest in inflorescence tissue.";
		Set result2 = obj.findWordsInArray(word2, text2);
		System.out.println(result2);
		
		System.out.println("===========================");
		
		String filename1 = "./src/key.txt";
		String filename2 = "./src/text.txt";		
		Set result3 = obj.findWordsInFile(filename1, filename2);
		System.out.println(result3);		
	}

}
