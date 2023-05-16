import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Experiment {
    Integer stringMinLength;
    Integer stringMaxLength;
    Integer arrayStartLength;
    Integer arrayMaxLength;
    Integer arrayLengthIncreaseStep;
    Integer repeat;
    public static void main(String[] args) {
        String filepath = "experiments.xml";
        File xmlFile = new File(filepath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();

            NodeList nodeList = document.getElementsByTagName("experiment");

            List<Experiment> laexperimentsgList = new ArrayList<>();
            for (int i = 0; i < nodeList.getLength(); i++) {
                laexperimentsgList.add(getExperiment(nodeList.item(i)));
            }

            // печатаем в консоль информацию по каждому объекту Language
            for (Experiment exp : laexperimentsgList) {
                System.out.println(exp.toString());
                exp.run();
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }

    }
    private static Experiment getExperiment(Node node) {
        Experiment exp = new Experiment();
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            exp.stringMinLength = Integer.parseInt(getTagValue("stringMinLength", element));
            exp.stringMaxLength = Integer.parseInt(getTagValue("stringMaxLength", element));
            exp.arrayStartLength = Integer.parseInt(getTagValue("arrayStartLength", element));
            exp.arrayMaxLength = Integer.parseInt(getTagValue("arrayMaxLength", element));
            exp.arrayLengthIncreaseStep = Integer.parseInt(getTagValue("arrayLengthIncreaseStep", element));
            exp.repeat = Integer.parseInt(getTagValue("repeat", element));
        }

        return exp;
    }

    // получаем значение элемента по указанному тегу
    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);
        return node.getNodeValue();
    }
    public void run() {
        for (var i = 0; i < repeat; i++) {
            CsvWriter csvWriter = new CsvWriter();
            List<String[]> lines = new ArrayList<>();
            var header = new String[]{"n", "iterations"};
            lines.add(header);
            for (var arraySize = arrayStartLength;
                 arraySize < arrayMaxLength;
                 arraySize += arrayLengthIncreaseStep){
                var array = generateArray(arraySize);
                var sorter = new Sorter();
                sorter.bubbleSort(array, String::compareTo);
                lines.add(
                        new String[] {arraySize.toString(),
                                sorter.operations.toString()});
            }
            try {
                csvWriter.writeToCsvFile(
                        lines, new File("result_" + i + ".csv"));
            }
            catch (Exception e) {
            }
        }
    }

    private String generateString() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'


        var random = new Random();
        var length = random.nextInt(stringMaxLength -stringMinLength)
                + stringMinLength;
        var sb = new StringBuilder();
        for (var i = 0; i < length; i++) {
            int randomLimitedInt = random.nextInt(
                    rightLimit - leftLimit) + leftLimit;
            sb.append((char) randomLimitedInt);
        }
        return sb.toString();
    }

    private String[] generateArray(Integer size){
        var array = new String[size];
        for (var i = 0; i < size; i++){
            array[i] = generateString();
        }
        return array;
    }


}
