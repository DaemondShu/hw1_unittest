package helper;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PairWiseHelper
{
    public static List<JsonNode> DeleteByPairWise(List<JsonNode> primaryTestCases, int inputSize)
    {
        ArrayList<JsonNode> primaryTestCasesCopy = new ArrayList<>(primaryTestCases);

        ArrayList<JsonNode> pairwiseTestCases = new ArrayList<>();


        int pairNumbers = 0;
        for (int i = 0; i < inputSize; i++)
        {
            pairNumbers += i;
        }

        for (JsonNode testCase : primaryTestCases)
        {
            //JsonNode testCase =  primaryTestCases.get(caseNum);

            List<Map<String, JsonNode>> pairSets = new ArrayList<>();
            for (int i = 0; i < pairNumbers; i++)  //2C3
            {
                pairSets.add(new TreeMap<>());
            }

            for (JsonNode testCaseB : primaryTestCasesCopy)
            {
                JsonNode inB = testCaseB.get("in");
                //JsonNode testCase =  primaryTestCases.get(caseNum);

                if (testCase.hashCode() == testCaseB.hashCode()) continue;
                int index = 0;
                for (int a = 0; a < inputSize - 1; a++)
                {
                    for (int b = a + 1; b < inputSize; b++)
                    {
                        //System.out.println(inB.toString() + a + b);
                        String pair = inB.get(a).asText() + "," + inB.get(b).asText();
                        Map<String, JsonNode> pairSet = pairSets.get(index);
                        if (!pairSet.containsKey(pair))
                        {
                            pairSets.get(index).put(pair, inB);
                        }
                        index++;
                    }
                }
            }

            JsonNode in = testCase.get("in");
            int index = 0;
            List<JsonNode> same_pair_id = new ArrayList<>();
            boolean fit = true;
            for (int a = 0; a < inputSize - 1; a++)
            {
                for (int b = a + 1; b < inputSize; b++)
                {
                    //System.out.println(in);
                    String pair = in.get(a).asText() + "," + in.get(b).asText();
                    Map<String, JsonNode> pairSet = pairSets.get(index);
                    if (!pairSet.containsKey(pair))
                    {
                        fit = false;
                        break;
                    } else
                    {
                        same_pair_id.add(pairSet.get(pair));
                    }
                    index++;
                }
            }

            if (fit == false) // 不需要删除的
            {
                pairwiseTestCases.add(testCase);

            } else //需要删除的
            {
                primaryTestCasesCopy.remove(testCase);
                System.out.println("delete " + testCase.toString() + " by " + same_pair_id.toString());
            }

        }

        return pairwiseTestCases;
    }
}
